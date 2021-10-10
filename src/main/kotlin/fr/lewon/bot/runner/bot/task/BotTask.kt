package fr.lewon.bot.runner.bot.task

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.logs.BotLogger
import fr.lewon.bot.runner.lifecycle.task.TaskState
import java.util.*

abstract class BotTask(val name: String, val bot: Bot, val initialDelayMillis: Long = 0) {
    var state = TaskState.PENDING
        private set
    private var taskResult: TaskResult? = null
    var executionDate: Date? = null
        private set
    val logger = BotLogger(bot.logger)

    /**
     * Executes the task and returns millis until next execution. Null if there is no next execution
     */
    fun run() {
        if (this.state == TaskState.PENDING) {
            this.state = TaskState.ACTIVE
        }
        try {
            this.taskResult = this.doExecute()
            this.taskResult?.tasksToCreate?.let { bot.startTasks(it) }
        } catch (e: Exception) {
            logger.error("An error occurred while processing [$name]", e)
            bot.crash(e.message)
        }
        val delayMillis = taskResult?.delay?.getDelayMillis()
        executionDate = delayMillis?.let { Date(System.currentTimeMillis() + it) }
    }

    protected abstract fun doExecute(): TaskResult

    fun crash() {
        this.state = TaskState.CRASHED
    }
}
