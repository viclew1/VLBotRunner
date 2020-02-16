package fr.lewon.bot.runner.errors;

import fr.lewon.bot.runner.lifecycle.ILifeCycleOperation;

public class InvalidOperationException extends Exception {

    private static final long serialVersionUID = -7690694602881356242L;

    public InvalidOperationException(ILifeCycleOperation operation, String initialState) {
        super("Invalid operation, [" + operation + "] can't be applied on the state [" + initialState + "]");
    }
}
