package fr.lewon.bot.runner.bot.operation;

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;

import java.util.List;

public abstract class BotOperation {

    private final String label;

    public BotOperation(String label) {
        this.label = label;
    }

    public abstract List<BotPropertyDescriptor> getNeededProperties();

    public abstract void run(BotPropertyStore botPropertyStore, BotPropertyStore paramsPropertyStore) throws Exception;

}
