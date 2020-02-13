package fr.lewon.bot.runner.lifecycle.task;

import fr.lewon.bot.runner.lifecycle.IOperation;
import fr.lewon.bot.runner.lifecycle.Transition;

public enum TaskOperation implements IOperation<TaskState> {

    START,
    STOP,
    TOGGLE_PAUSE;

    private final Transition<TaskState>[] transitions;

    TaskOperation(Transition<TaskState>... transitions) {
        this.transitions = transitions;
    }

    @Override
    public Transition<TaskState>[] getTransitions() {
        return this.transitions;
    }
}
