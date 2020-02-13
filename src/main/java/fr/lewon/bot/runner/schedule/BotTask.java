package fr.lewon.bot.runner.schedule;

import fr.lewon.bot.runner.lifecycle.bot.BotState;
import fr.lewon.bot.runner.util.BeanUtil;
import fr.lewon.bot.runner.util.BotTaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;

public abstract class BotTask implements Trigger, Runnable {

    private static final BotTaskScheduler botTaskScheduler = BeanUtil.getBean(BotTaskScheduler.class);

    private long executionTimeMillis = System.currentTimeMillis();
    private long delayMillis;
    private BotState botState = BotState.PENDING;

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

    public BotState getBotState() {
        return this.botState;
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
        if (this.botState == BotState.PENDING) {
            this.botState = BotState.ACTIVE;
            return new Date();
        }
        if (this.taskResult != null && this.taskResult.getTasksToCreate() != null) {
            botTaskScheduler.startTaskAutoExecution(this.taskResult.getTasksToCreate());
        }
        if (this.taskResult != null && this.taskResult.getDelay() != null) {
            this.delayMillis = this.taskResult.getDelay().getDelayMillis();
            return new Date(this.executionTimeMillis + this.delayMillis);
        }
        this.botState = BotState.STOPPED;
        return null;
    }
}
