package fr.lewon.bot.runner.bot.task

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.logs.BotLogger
import fr.lewon.bot.runner.lifecycle.task.TaskState
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.TriggerContext
import java.util.*

abstract class BotTask(val name: String, val bot: Bot, private val initialDelayMillis: Long = 0) : Trigger, Runnable {
    var state = TaskState.PENDING
        private set
    private var taskResult: TaskResult? = null
    var executionDate: Date? = null
        private set
    val logger = BotLogger(bot.logger)

    override fun run() {
        try {
            this.taskResult = this.doExecute()
        } catch (e: Exception) {
            logger.error("An error occurred while processing [$name]", e)
            bot.crash(e.message)
        }

    }

    protected abstract fun doExecute(): TaskResult

    override fun nextExecutionTime(triggerContext: TriggerContext): Date? {
        executionDate = defineExecutionDate(triggerContext)
        if (executionDate == null) {
            bot.cancelTasks(listOf(this))
        }
        return executionDate
    }

    private fun defineExecutionDate(triggerContext: TriggerContext): Date? {
        if (this.state == TaskState.CRASHED) {
            return null
        }
        try {
            if (this.state == TaskState.PENDING) {
                this.state = TaskState.ACTIVE
                return Date(System.currentTimeMillis() + initialDelayMillis)
            }
            this.taskResult?.tasksToCreate
                ?.let { bot.startTasks(it) }

            this.taskResult?.delay?.getDelayMillis()
                ?.takeIf { it > 0 }
                ?.let { triggerContext.lastCompletionTime()?.time?.plus(it) }
                ?.let { return Date(it) }
        } catch (e: Exception) {
            logger.error("An error occurred while fetching next [$name] execution time", e)
            bot.crash(e.message)
        }

        return null
    }

    fun crash() {
        this.state = TaskState.CRASHED
    }
}
