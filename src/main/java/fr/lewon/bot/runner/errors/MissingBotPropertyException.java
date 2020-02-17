package fr.lewon.bot.runner.errors;

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;

public class MissingBotPropertyException extends Exception {

    private static final long serialVersionUID = 7659151523605467729L;

    public MissingBotPropertyException(BotPropertyDescriptor descriptor) {
        super("Missing property for bot property descriptor : [" + descriptor + "]");
    }
}
