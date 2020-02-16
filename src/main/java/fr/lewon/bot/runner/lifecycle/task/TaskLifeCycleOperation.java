package fr.lewon.bot.runner.lifecycle.task;

import fr.lewon.bot.runner.lifecycle.ILifeCycleOperation;
import fr.lewon.bot.runner.lifecycle.Transition;

public enum TaskLifeCycleOperation implements ILifeCycleOperation<TaskState> {

    START(new Transition<>(TaskState.ACTIVE, TaskState.PENDING)),
    STOP(new Transition<>(TaskState.DISPOSED, TaskState.ACTIVE));

    private final Transition<TaskState>[] transitions;

    TaskLifeCycleOperation(Transition<TaskState>... transitions) {
        this.transitions = transitions;
    }

    @Override
    public Transition<TaskState>[] getTransitions() {
        return this.transitions;
    }
}
