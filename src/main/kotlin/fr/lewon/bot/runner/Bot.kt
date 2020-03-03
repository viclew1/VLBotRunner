package fr.lewon.bot.runner

import fr.lewon.bot.runner.bot.logs.BotLogger
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.def.RestartBotTask
import fr.lewon.bot.runner.errors.InvalidOperationException
import fr.lewon.bot.runner.lifecycle.bot.BotLifeCycleOperation
import fr.lewon.bot.runner.lifecycle.bot.BotState
import fr.lewon.bot.runner.session.AbstractSessionManager
import fr.lewon.bot.runner.util.BeanUtil
import fr.lewon.bot.runner.util.BotTaskScheduler

class Bot(val botPropertyStore: BotPropertyStore, private val initialTasksGenerator: (Bot) -> List<BotTask>, private val botOperations: List<BotOperation>, val sessionManager: AbstractSessionManager, val logger: BotLogger = BotLogger()) {

    private val tasks = ArrayList<BotTask>()
    var state = BotState.PENDING
        private set

    @Throws(InvalidOperationException::class)
    fun start() {
        this.state = BotLifeCycleOperation.START.getResultingState(this.state)
        startTasks(this.initialTasksGenerator.invoke(this))
    }

    @Throws(InvalidOperationException::class)
    fun stop() {
        this.state = BotLifeCycleOperation.STOP.getResultingState(this.state)
        cancelTasks(tasks)
        sessionManager.forceRefresh()
    }

    fun crash() {
        this.state = BotLifeCycleOperation.CRASH.getResultingState(this.state)
        botTaskScheduler.cancelTaskAutoExecution(this.tasks)
        sessionManager.forceRefresh()
        this.tasks.forEach { t -> t.crash() }
        this.tasks.clear()
        (botPropertyStore.getByKey("auto_restart_timer") as Int?)?.toLong()?.let {
            startTask(RestartBotTask(this, it * 1000))
        }
    }

    fun getTasks(): List<BotTask> {
        return ArrayList(this.tasks)
    }

    fun startTask(botTask: BotTask) {
        if (!tasks.contains(botTask)) {
            botTaskScheduler.startTaskAutoExecution(botTask)
            tasks.add(botTask)
        }
    }

    fun cancelTask(botTask: BotTask) {
        if (tasks.contains(botTask)) {
            botTaskScheduler.cancelTaskAutoExecution(botTask)
            tasks.remove(botTask)
        }
    }

    fun startTasks(toStart: List<BotTask>) {
        botTaskScheduler.startTaskAutoExecution(toStart)
        this.tasks.addAll(toStart)
    }

    fun cancelTasks(toCancel: List<BotTask>) {
        botTaskScheduler.cancelTaskAutoExecution(toCancel)
        this.tasks.removeAll(toCancel)
    }

    companion object {
        private val botTaskScheduler: BotTaskScheduler = BeanUtil.getBean()
    }
}
