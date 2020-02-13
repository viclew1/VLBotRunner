package fr.lewon.bot.runner.errors;

public class InvalidOperationException extends Exception {

    private static final long serialVersionUID = -7690694602881356242L;

    public InvalidOperationException(Object operation, Object initialState) {
        super("Invalid operation, [" + operation + "] can't be applied on the state [" + initialState + "]");
    }
}
