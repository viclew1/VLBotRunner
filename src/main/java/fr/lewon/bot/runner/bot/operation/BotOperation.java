package fr.lewon.bot.runner.bot.operation;

import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;

import java.util.List;

public abstract class BotOperation {

    private final String label;

    public BotOperation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public abstract List<BotPropertyDescriptor> getNeededProperties(Bot bot);

    public abstract Object run(Bot bot, BotPropertyStore paramsPropertyStore) throws Exception;

}
