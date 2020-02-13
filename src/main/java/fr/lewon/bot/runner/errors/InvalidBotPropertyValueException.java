package fr.lewon.bot.runner.errors;

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;

public class InvalidBotPropertyValueException extends Exception {

    private static final long serialVersionUID = -5592722151152924032L;

    public InvalidBotPropertyValueException(String value, BotPropertyDescriptor descriptor) {
        this(value, descriptor, null);
    }

    public InvalidBotPropertyValueException(String value, BotPropertyDescriptor descriptor, Exception cause) {
        super("Value [" + value + "] can't be parsed to bot property descriptor : [" + descriptor + "]", cause);
    }
}
