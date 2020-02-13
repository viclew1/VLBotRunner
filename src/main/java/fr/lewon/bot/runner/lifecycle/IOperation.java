package fr.lewon.bot.runner.lifecycle;

import fr.lewon.bot.runner.errors.InvalidOperationException;

public interface IOperation<T> {

    default T getResultingState(T initialState) throws InvalidOperationException {
        for (Transition<T> transition : this.getTransitions()) {
            for (T state : transition.getFromStates()) {
                if (state == initialState) {
                    return transition.getToState();
                }
            }
        }
        throw new InvalidOperationException(this, initialState.toString());
    }

    Transition<T>[] getTransitions();

}
