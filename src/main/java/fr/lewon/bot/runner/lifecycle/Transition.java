package fr.lewon.bot.runner.lifecycle;

public class Transition<T> {

    private T[] fromStates;
    private T toState;

    public Transition(T toState, T... fromStates) {
        this.fromStates = fromStates;
        this.toState = toState;
    }

    public T[] getFromStates() {
        return this.fromStates;
    }

    public T getToState() {
        return this.toState;
    }
}