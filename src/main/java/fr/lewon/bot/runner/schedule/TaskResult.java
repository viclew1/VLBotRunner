package fr.lewon.bot.runner.schedule;

import java.util.ArrayList;
import java.util.List;

public class TaskResult {

    private Delay delay;
    private List<BotTask> tasksToCreate;

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

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    public List<BotTask> getTasksToCreate() {
        return this.tasksToCreate;
    }

    public void setTasksToCreate(List<BotTask> tasksToCreate) {
        this.tasksToCreate = tasksToCreate;
    }
}
