package fr.lewon.bot.runner.errors

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor

class MissingBotPropertyException(descriptor: BotPropertyDescriptor) : Exception("Missing property for bot property descriptor : [$descriptor]") {
    companion object {
        private const val serialVersionUID = 7659151523605467729L
    }
}
