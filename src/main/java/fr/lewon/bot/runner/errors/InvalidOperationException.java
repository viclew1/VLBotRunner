package fr.lewon.bot.runner.errors;

import fr.lewon.bot.runner.lifecycle.IOperation;

public class InvalidOperationException extends Exception {

    private static final long serialVersionUID = -7690694602881356242L;

    public InvalidOperationException(IOperation operation, String initialState) {
        super("Invalid operation, [" + operation + "] can't be applied on the state [" + initialState + "]");
    }
}
