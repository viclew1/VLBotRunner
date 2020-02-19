package fr.lewon.bot.runner.util

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException
import fr.lewon.bot.runner.errors.MissingBotPropertyException
import org.springframework.stereotype.Component

@Component
class BotPropertyParser {

    @Throws(InvalidBotPropertyValueException::class, MissingBotPropertyException::class)
    fun parseParams(params: Map<String, String?>, targetBotPropertyDescriptors: List<BotPropertyDescriptor>): BotPropertyStore {

        val botPropertyStore = BotPropertyStore()
        for (propertyDescriptor in targetBotPropertyDescriptors) {
            if (params.containsKey(propertyDescriptor.key)) {
                botPropertyStore.addProperty(propertyDescriptor, this.parse(propertyDescriptor, params[propertyDescriptor.key]))
            } else if (!propertyDescriptor.isNeeded) {
                botPropertyStore.addProperty(propertyDescriptor, propertyDescriptor.defaultValue)
            } else {
                throw MissingBotPropertyException(propertyDescriptor)
            }
        }
        return botPropertyStore
    }

    @Throws(InvalidBotPropertyValueException::class)
    fun <T> parse(descriptor: BotPropertyDescriptor, value: String?): T? {
        if (value.isNullOrBlank()) {
            if (descriptor.isNullable) {
                return null
            }
            if (!descriptor.isNeeded) {
                return descriptor.defaultValue as T
            }
            throw InvalidBotPropertyValueException(value, descriptor)
        }

        val parsedValue: T
        try {
            parsedValue = descriptor.type.parse(value) as T
        } catch (e: Exception) {
            throw InvalidBotPropertyValueException(value, descriptor, e)
        }

        if (descriptor.acceptedValues.isEmpty() || descriptor.acceptedValues.contains(parsedValue)) {
            return parsedValue
        }
        throw InvalidBotPropertyValueException(value, descriptor)
    }

}
