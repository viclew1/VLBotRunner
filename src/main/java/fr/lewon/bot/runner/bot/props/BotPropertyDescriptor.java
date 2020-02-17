package fr.lewon.bot.runner.bot.props;

import java.util.Arrays;

public class BotPropertyDescriptor {

    private final String key;
    private final BotPropertyType type;
    private final String description;
    private final boolean needed;
    private final boolean nullable;
    private final Object defaultValue;
    private final Object[] acceptedValues;

    public BotPropertyDescriptor(String key, BotPropertyType type, Object defaultValue, String description, boolean needed, boolean nullable, Object... acceptedValues) {
        this.key = key;
        this.type = type;
        this.defaultValue = defaultValue;
        this.description = description;
        this.needed = needed;
        this.nullable = nullable;
        this.acceptedValues = acceptedValues;
    }

    public String getKey() {
        return this.key;
    }

    public BotPropertyType getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isNeeded() {
        return this.needed;
    }

    public Object[] getAcceptedValues() {
        return this.acceptedValues;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public String toString() {
        return "BotPropertyDescriptor{" +
                "key='" + this.key + '\'' +
                ", type=" + this.type +
                ", description='" + this.description + '\'' +
                ", needed=" + this.needed +
                ", nullable=" + this.nullable +
                ", defaultValue=" + this.defaultValue +
                ", acceptedValues=" + Arrays.toString(this.acceptedValues) +
                '}';
    }
}
