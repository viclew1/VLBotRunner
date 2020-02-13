package fr.lewon.bot.runner.schedule;

import fr.lewon.bot.runner.lifecycle.bot.BotState;
import fr.lewon.bot.runner.lifecycle.task.TaskState;
import fr.lewon.bot.runner.util.BeanUtil;
import fr.lewon.bot.runner.util.BotTaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;

public abstract class BotTask implements Trigger, Runnable {

    private static final BotTaskScheduler botTaskScheduler = BeanUtil.getBean(BotTaskScheduler.class);

    private long executionTimeMillis = System.currentTimeMillis();
    private long delayMillis;
    private TaskState state = TaskState.PENDING;

    private TaskResult taskResult;

    @Override
    public final void run() {
        try {
            this.taskResult = this.doExecute();
            this.executionTimeMillis = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getMillisUntilExec() {
        return Math.max(0, (this.executionTimeMillis + this.delayMillis - System.currentTimeMillis()));
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
     * @return
     */
    protected abstract TaskResult doExecute() throws Exception;

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        if (this.state == TaskState.PENDING) {
            this.state = TaskState.ACTIVE;
            return new Date();
        }
        if (this.taskResult != null && this.taskResult.getTasksToCreate() != null) {
            botTaskScheduler.startTaskAutoExecution(this.taskResult.getTasksToCreate());
        }
        if (this.taskResult != null && this.taskResult.getDelay() != null) {
            this.delayMillis = this.taskResult.getDelay().getDelayMillis();
            return new Date(this.executionTimeMillis + this.delayMillis);
        }
        this.state = TaskState.DISPOSED;
        return null;
    }
}
