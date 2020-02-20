package fr.lewon.bot.runner.errors

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor

class InvalidBotPropertyValueException @JvmOverloads constructor(value: String?, descriptor: BotPropertyDescriptor, cause: Exception? = null) : Exception("Value [$value] can't be parsed to bot property descriptor : [$descriptor]", cause) {
    companion object {
        private const val serialVersionUID = -5592722151152924032L
    }
}
