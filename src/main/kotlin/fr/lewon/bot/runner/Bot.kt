package fr.lewon.bot.runner

import fr.lewon.bot.runner.bot.logs.BotLogger
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.def.RestartBotTask
import fr.lewon.bot.runner.lifecycle.bot.BotState
import fr.lewon.bot.runner.session.AbstractSessionManager
import fr.lewon.bot.runner.util.BeanUtil
import fr.lewon.bot.runner.util.BotTaskScheduler

class Bot(
    val botPropertyStore: BotPropertyStore,
    private val initialTasksGenerator: (Bot) -> List<BotTask>,
    val sessionManager: AbstractSessionManager,
    val logger: BotLogger = BotLogger()
) {

    private val tasks = ArrayList<BotTask>()
    var state = BotState.PENDING
        private set
    val sharedProperties = HashMap<String, Any>()

    fun start() {
        cancelTasks(tasks)
        sessionManager.forceRefresh()
        startTasks(this.initialTasksGenerator.invoke(this))
        this.state = BotState.ACTIVE
    }

    fun stop() {
        cancelTasks(tasks)
        sharedProperties.clear()
        this.state = BotState.STOPPED
    }

    fun crash(message: String? = null) {
        this.tasks.forEach { t -> t.crash() }
        cancelTasks(tasks)
        this.state = BotState.CRASHED
        message?.let { logger.error(" BOT CRASHED ! Reason : $message") }
        (botPropertyStore.getByKey("auto_restart_timer") as Int?)
            ?.let {
                startTask(RestartBotTask(this, it * 1000L * 60))
                logger.error("Trying to restart bot in $it minutes")
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
