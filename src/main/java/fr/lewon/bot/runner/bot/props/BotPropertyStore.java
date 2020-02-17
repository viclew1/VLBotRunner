package fr.lewon.bot.runner.bot.props;

import java.util.HashMap;

public class BotPropertyStore extends HashMap<BotPropertyDescriptor, Object> {

    private static final long serialVersionUID = 8101778657485578888L;

    public BotPropertyStore() {
    }

    public BotPropertyStore(BotPropertyStore botPropertyStore) {
        super(botPropertyStore);
    }

    public Object getByKey(String key) {
        return this.get(this.keySet().stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst().orElse(null));
    }

    public void addProperty(BotPropertyDescriptor key, Object value) {
        this.put(key, value);
    }

}
