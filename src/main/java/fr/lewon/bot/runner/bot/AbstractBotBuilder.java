package fr.lewon.bot.runner.bot;

import fr.lewon.bot.runner.bot.operation.BotOperation;
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.bot.task.BotTask;
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;
import fr.lewon.bot.runner.errors.MissingBotPropertyException;
import fr.lewon.bot.runner.util.BeanUtil;
import fr.lewon.bot.runner.util.BotPropertyParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractBotBuilder {

    private static BotPropertyParser botPropertyParser = BeanUtil.getBean(BotPropertyParser.class);

    private String botName;
    private List<BotPropertyDescriptor> botPropertyDescriptors;

    public AbstractBotBuilder(String botName, BotPropertyDescriptor... botPropertyDescriptors) {
        this(botName, Arrays.asList(botPropertyDescriptors));
    }

    public AbstractBotBuilder(String botName, List<BotPropertyDescriptor> botPropertyDescriptors) {
        this.botName = botName;
        this.botPropertyDescriptors = Collections.unmodifiableList(botPropertyDescriptors);
    }

    public Bot buildBot(Map<String, String> properties) throws InvalidBotPropertyValueException, MissingBotPropertyException {
        BotPropertyStore botPropertyStore = botPropertyParser.parseParams(properties, this.botPropertyDescriptors);
        return new Bot(botPropertyStore, (b) -> this.getInitialTasks(b), this.getBotOperations());
    }

    protected abstract List<BotOperation> getBotOperations();

    protected abstract List<BotTask> getInitialTasks(Bot bot);

}
