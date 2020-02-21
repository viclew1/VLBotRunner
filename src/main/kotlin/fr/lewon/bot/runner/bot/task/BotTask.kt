package fr.lewon.bot.runner.bot.task

import fr.lewon.bot.runner.bot.Bot
import fr.lewon.bot.runner.lifecycle.task.TaskLifeCycleOperation
import fr.lewon.bot.runner.lifecycle.task.TaskState
import fr.lewon.bot.runner.util.BeanUtil
import fr.lewon.bot.runner.util.BotTaskScheduler
import org.slf4j.LoggerFactory
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.TriggerContext
import java.util.*

abstract class BotTask(private val bot: Bot) : Trigger, Runnable {
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
            this.state = TaskState.CRASHED
            LOGGER.error("An error occurred while processing [" + this.javaClass.canonicalName + "]", e)
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
                return Date()
            }
            this.taskResult?.tasksToCreate
                    ?.let { botTaskScheduler.startTaskAutoExecution(it) }
            this.taskResult?.delay?.getDelayMillis()
                    ?.let { executionTimeMillis?.plus(it) }
                    ?.let { return Date(it) }
            this.state = TaskLifeCycleOperation.STOP.getResultingState(this.state)
        } catch (e: Exception) {
            LOGGER.error("An error occurred while fetching next [" + this.javaClass.canonicalName + "] execution time", e)
            this.state = TaskState.CRASHED
        }

        return null
    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(BotTask::class.java)
        private val botTaskScheduler = BeanUtil.getBean(BotTaskScheduler::class.java)
    }
}
