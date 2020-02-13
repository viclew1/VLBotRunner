package fr.lewon.bot.runner.bot.props;

import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;

import java.util.HashMap;
import java.util.Map;

public class BotPropertyStore {

    private Map<BotPropertyDescriptor, Object> properties;

    public BotPropertyStore() {
        this.properties = new HashMap<>();
    }

    public BotPropertyStore(BotPropertyStore botPropertyStore) {
        this.properties = new HashMap<>(botPropertyStore.properties);
    }

    public Object getProperty(String key) {
        return this.getProperty(this.properties.keySet().stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst().orElse(null));
    }

    public Object getProperty(BotPropertyDescriptor key) {
        return this.properties.get(key);
    }

    public void addProperty(BotPropertyDescriptor key, String value) throws InvalidBotPropertyValueException {
        this.properties.put(key, key.parse(value));
    }
}
