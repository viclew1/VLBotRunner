package fr.lewon.bot.runner.bot;

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.bot.task.BotTask;
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractBotBuilder {

    private String botName;
    private List<BotPropertyDescriptor> botPropertyDescriptors;

    public AbstractBotBuilder(String botName, BotPropertyDescriptor... botPropertyDescriptors) {
        this(botName, Arrays.asList(botPropertyDescriptors));
    }

    public AbstractBotBuilder(String botName, List<BotPropertyDescriptor> botPropertyDescriptors) {
        this.botName = botName;
        this.botPropertyDescriptors = Collections.unmodifiableList(botPropertyDescriptors);
    }

    public Bot buildBot(Map<String, String> properties) throws InvalidBotPropertyValueException {
        BotPropertyStore botPropertyStore = new BotPropertyStore();
        Map<BotPropertyDescriptor, String> appliedProperties = this.botPropertyDescriptors.stream()
                .collect(Collectors.toMap(b -> b, b -> properties.get(b.getKey())));
        for (Map.Entry<BotPropertyDescriptor, String> e : appliedProperties.entrySet()) {
            botPropertyStore.addProperty(e.getKey(), e.getValue());
        }
        return new Bot(botPropertyStore, (b) -> this.getInitialTasks(b));
    }


    protected abstract List<BotTask> getInitialTasks(Bot bot);

}
