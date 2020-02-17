package fr.lewon.bot.runner.util;

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;
import fr.lewon.bot.runner.errors.MissingBotPropertyException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BotPropertyParser {

    public BotPropertyStore parseParams(Map<String, String> params, List<BotPropertyDescriptor> targetBotPropertyDescriptors) throws InvalidBotPropertyValueException, MissingBotPropertyException {

        BotPropertyStore botPropertyStore = new BotPropertyStore();
        for (BotPropertyDescriptor propertyDescriptor : targetBotPropertyDescriptors) {
            if (!params.containsKey(propertyDescriptor.getKey()) && propertyDescriptor.isNeeded()) {
                throw new MissingBotPropertyException(propertyDescriptor);
            }
            botPropertyStore.addProperty(propertyDescriptor, this.parse(propertyDescriptor, params.get(propertyDescriptor.getKey())));
        }
        return botPropertyStore;
    }

    public Object parse(BotPropertyDescriptor descriptor, String value) throws InvalidBotPropertyValueException {
        if (value == null || value.isBlank()) {
            if (descriptor.isNullable()) {
                return null;
            }
            throw new InvalidBotPropertyValueException(value, descriptor);
        }

        Object parsedValue;
        try {
            parsedValue = descriptor.getType().parse(value);
        } catch (Exception e) {
            throw new InvalidBotPropertyValueException(value, descriptor, e);
        }

        if (descriptor.getAcceptedValues() == null || descriptor.getAcceptedValues().length == 0) {
            return parsedValue;
        }

        for (Object acceptedValue : descriptor.getAcceptedValues()) {
            if (acceptedValue.equals(parsedValue)) {
                return parsedValue;
            }
        }
        throw new InvalidBotPropertyValueException(value, descriptor);
    }

}
