package fr.lewon.bot.runner.bot.task;

import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.lifecycle.task.TaskLifeCycleOperation;
import fr.lewon.bot.runner.lifecycle.task.TaskState;
import fr.lewon.bot.runner.util.BeanUtil;
import fr.lewon.bot.runner.util.BotTaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;

public abstract class BotTask implements Trigger, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotTask.class);
    private static final BotTaskScheduler botTaskScheduler = BeanUtil.getBean(BotTaskScheduler.class);

    private Bot bot;
    private Long executionTimeMillis;
    private TaskState state = TaskState.PENDING;
    private TaskResult taskResult;

    public BotTask(Bot bot) {
        this.bot = bot;
    }

    @Override
    public final void run() {
        try {
            this.taskResult = this.doExecute(this.bot);
            this.executionTimeMillis = System.currentTimeMillis();
        } catch (Exception e) {
            this.state = TaskState.CRASHED;
            LOGGER.error("An error occurred while processing [" + this.getClass().getCanonicalName() + "]", e);
        }
    }

    /**
     * Returns the display name of this task
     *
     * @return
     */
    public abstract String getLabel();

    /**
     * Executes the bot task and returns the delay until next execution in millis
     *
     * @param bot
     * @return
     * @throws Exception
     */
    protected abstract TaskResult doExecute(Bot bot) throws Exception;

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        if (this.state == TaskState.CRASHED) {
            return null;
        }
        try {
            if (this.state == TaskState.PENDING) {
                this.state = TaskLifeCycleOperation.START.getResultingState(this.state);
                return new Date();
            }
            if (this.taskResult != null && this.taskResult.getTasksToCreate() != null) {
                botTaskScheduler.startTaskAutoExecution(this.taskResult.getTasksToCreate());
            }
            if (this.taskResult != null && this.taskResult.getDelay() != null) {
                return new Date(this.executionTimeMillis + this.taskResult.getDelay().getDelayMillis());
            }
            this.state = TaskLifeCycleOperation.STOP.getResultingState(this.state);
        } catch (Exception e) {
            LOGGER.error("An error occurred while fetching next [" + this.getClass().getCanonicalName() + "] execution time", e);
            this.state = TaskState.CRASHED;
        }
        return null;
    }

    public TaskState getState() {
        return this.state;
    }

    public Long getExecutionTimeMillis() {
        return this.executionTimeMillis;
    }
}
