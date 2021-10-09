package fr.lewon.bot.runner.errors

import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor

class MissingBotPropertyException(descriptor: BotPropertyDescriptor) :
    Exception("Missing property for bot property descriptor : [$descriptor]")