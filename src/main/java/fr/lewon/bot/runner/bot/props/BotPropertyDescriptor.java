package fr.lewon.bot.runner.bot.props;

import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;

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

    public Object parse(String value) throws InvalidBotPropertyValueException {
        if (value == null || value.isBlank()) {
            if (this.nullable) {
                return null;
            }
            if (!this.needed) {
                return this.defaultValue;
            }
            throw new InvalidBotPropertyValueException(value, this);
        }

        Object parsedValue = null;
        try {
            parsedValue = this.type.parse(value);
        } catch (Exception e) {
            throw new InvalidBotPropertyValueException(value, this, e);
        }

        if (this.acceptedValues == null || this.acceptedValues.length == 0) {
            return parsedValue;
        }

        for (Object acceptedValue : this.acceptedValues) {
            if (acceptedValue.equals(parsedValue)) {
                return parsedValue;
            }
        }
        throw new InvalidBotPropertyValueException(value, this);
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
