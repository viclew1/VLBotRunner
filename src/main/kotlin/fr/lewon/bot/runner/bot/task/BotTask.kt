package fr.lewon.bot.runner.bot.task

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.lifecycle.task.TaskLifeCycleOperation
import fr.lewon.bot.runner.lifecycle.task.TaskState
import org.slf4j.LoggerFactory
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.TriggerContext
import java.util.*

abstract class BotTask @JvmOverloads constructor(private val bot: Bot, private val initialDelayMillis: Long = 0) : Trigger, Runnable {
    var executionTimeMillis: Long? = null
        private set
    private var state = TaskState.PENDING
    private var taskResult: TaskResult? = null

    /**
     * Returns the display name of this task
     *
     * @return
     */
    abstract fun getLabel(): String

    override fun run() {
        try {
            this.taskResult = this.doExecute(this.bot)
            this.executionTimeMillis = System.currentTimeMillis()
        } catch (e: Exception) {
            LOGGER.error("An error occurred while processing [" + this.javaClass.canonicalName + "]", e)
            bot.crash()
        }

    }

    /**
     * Executes the bot task and returns the delay until next execution in millis
     *
     * @param bot
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    protected abstract fun doExecute(bot: Bot): TaskResult

    override fun nextExecutionTime(triggerContext: TriggerContext): Date? {
        if (this.state == TaskState.CRASHED) {
            return null
        }
        try {
            if (this.state == TaskState.PENDING) {
                this.state = TaskLifeCycleOperation.START.getResultingState(this.state)
                return Date(System.currentTimeMillis() + initialDelayMillis)
            }
            this.taskResult?.tasksToCreate
                    ?.let { bot.startTasks(it) }
            this.taskResult?.delay?.getDelayMillis()
                    ?.let { executionTimeMillis?.plus(it) }
                    ?.let { return Date(it) }
            this.state = TaskLifeCycleOperation.STOP.getResultingState(this.state)
        } catch (e: Exception) {
            LOGGER.error("An error occurred while fetching next [" + this.javaClass.canonicalName + "] execution time", e)
            bot.crash()
        }

        return null
    }

    fun crash() {
        this.state = TaskState.CRASHED
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(BotTask::class.java)
    }
}
