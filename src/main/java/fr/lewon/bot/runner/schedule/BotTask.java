package fr.lewon.bot.runner.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class BotTask {

    @Autowired
    private CentralScheduler centralScheduler;

    @Async
    @Scheduled(fixedDelayString = "${bot.task.execution.delay}")
    public final void execute() throws InterruptedException {
        Long delay = this.doExecute();
        if (delay == null) {
            this.centralScheduler.cancelTaskAutoExecution(this);
        } else {
            Thread.sleep(delay);
        }
    }

    /**
     * Executes the bot task and returns the delay until next execution in millis
     *
     * @return
     */
    protected abstract Long doExecute();

}
