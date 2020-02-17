package fr.lewon.bot.runner.errors;

import fr.lewon.bot.runner.bot.operation.BotOperation;

public class BotOperationExecutionException extends Exception {

    private static final long serialVersionUID = 8125206445264588526L;

    public BotOperationExecutionException(BotOperation botOperation, Exception cause) {
        super("An error occurred when running [" + botOperation.getClass().getCanonicalName() + "]", cause);
    }
}
