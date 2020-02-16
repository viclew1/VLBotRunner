package fr.lewon.bot.runner.util;

import fr.lewon.bot.runner.bot.task.BotTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class BotTaskScheduler {

    @Autowired
    private TaskScheduler scheduler;

    // A map for keeping scheduled tasks
    private Map<BotTask, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void cancelTaskAutoExecution(BotTask task) {
        this.jobsMap.remove(task).cancel(true);
    }

    public void cancelTaskAutoExecution(List<BotTask> tasks) {
        tasks.forEach(this::cancelTaskAutoExecution);
    }

    public void startTaskAutoExecution(BotTask task) {
        this.jobsMap.put(task, this.scheduler.schedule(task, task));
    }

    public void startTaskAutoExecution(List<BotTask> tasks) {
        tasks.forEach(this::startTaskAutoExecution);
    }

    public List<BotTask> getTasks() {
        return new ArrayList<>(this.jobsMap.keySet());
    }
}
