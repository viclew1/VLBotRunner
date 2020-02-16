package fr.lewon.bot.runner.bot.task;

import java.util.ArrayList;
import java.util.List;

public class TaskResult {

    private final Delay delay;
    private final List<BotTask> tasksToCreate;

    public TaskResult() {
        this((Delay) null);
    }

    public TaskResult(List<BotTask> tasksToCreate) {
        this(null, tasksToCreate);
    }

    public TaskResult(Delay delay) {
        this(delay, new ArrayList<>());
    }

    public TaskResult(Delay delay, List<BotTask> tasksToCreate) {
        this.delay = delay;
        this.tasksToCreate = tasksToCreate;
    }

    public Delay getDelay() {
        return this.delay;
    }

    public List<BotTask> getTasksToCreate() {
        return this.tasksToCreate;
    }

}
