package fr.lewon.bot.runner

import fr.lewon.bot.runner.bot.logs.BotLogger
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.def.RestartBotTask
import fr.lewon.bot.runner.lifecycle.bot.BotState
import fr.lewon.bot.runner.session.AbstractSessionManager
import fr.lewon.bot.runner.util.BotTaskScheduler

class Bot(
    val botPropertyStore: BotPropertyStore,
    private val initialTasksGenerator: (Bot) -> List<BotTask>,
    val sessionManager: AbstractSessionManager,
    val logger: BotLogger = BotLogger()
) {

    val tasks = ArrayList<BotTask>()
    var state = BotState.PENDING
        private set
    val sharedProperties = HashMap<String, Any>()

    fun start() {
        cancelTasks()
        sessionManager.forceRefresh()
        startTasks(this.initialTasksGenerator.invoke(this))
        this.state = BotState.ACTIVE
    }

    fun stop() {
        cancelTasks()
        sharedProperties.clear()
        this.state = BotState.STOPPED
    }

    fun crash(message: String? = null) {
        this.tasks.forEach { t -> t.crash() }
        cancelTasks()
        this.state = BotState.CRASHED
        message?.let { logger.error(" BOT CRASHED ! Reason : $message") }
        (botPropertyStore.getByKey("auto_restart_timer") as Int?)
            ?.let {
                startTask(RestartBotTask(this, it * 1000L * 60))
                logger.error("Trying to restart bot in $it minutes")
            }
    }

    fun startTask(botTask: BotTask) {
        if (!tasks.contains(botTask)) {
            BotTaskScheduler.startTaskAutoExecution(botTask)
            tasks.add(botTask)
        }
    }

    fun startTasks(toStart: List<BotTask>) {
        BotTaskScheduler.startTaskAutoExecution(toStart)
        this.tasks.addAll(toStart)
    }

    private fun cancelTasks() {
        BotTaskScheduler.cancelTaskAutoExecution(this)
        this.tasks.clear()
    }

}
