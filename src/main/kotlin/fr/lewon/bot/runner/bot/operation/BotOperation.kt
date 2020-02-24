package fr.lewon.bot.runner.bot.operation

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore

abstract class BotOperation(val label: String) {

    abstract fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor>

    @Throws(Exception::class)
    abstract fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult

}
