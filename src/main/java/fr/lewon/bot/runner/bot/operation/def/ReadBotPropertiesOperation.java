package fr.lewon.bot.runner.bot.operation.def;

import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.bot.operation.BotOperation;
import fr.lewon.bot.runner.bot.operation.OperationResult;
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;

import java.util.ArrayList;
import java.util.List;

public class ReadBotPropertiesOperation extends BotOperation {

    public ReadBotPropertiesOperation() {
        super("Read bot properties");
    }

    @Override
    public List<BotPropertyDescriptor> getNeededProperties(Bot bot) {
        return new ArrayList<>();
    }

    @Override
    public Object run(Bot bot, BotPropertyStore paramsPropertyStore) throws Exception {
        return new OperationResult(true, "Bot properties retrieved", bot.getBotPropertyStore());
    }
}