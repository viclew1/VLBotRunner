package fr.lewon.bot.runner.util;

import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.bot.operation.BotOperation;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.errors.BotOperationExecutionException;
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;
import fr.lewon.bot.runner.errors.MissingBotPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BotOperationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotOperationRunner.class);

    @Autowired
    private BotPropertyParser botPropertyParser;

    public Object runOperation(BotOperation botOperation, Bot bot, Map<String, String> params)
            throws InvalidBotPropertyValueException, BotOperationExecutionException, MissingBotPropertyException {

        BotPropertyStore paramStore = this.botPropertyParser.parseParams(params, botOperation.getNeededProperties(bot));
        try {
            return botOperation.run(bot, paramStore);
        } catch (Exception e) {
            throw new BotOperationExecutionException(botOperation, e);
        }
    }

}
