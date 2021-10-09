package fr.lewon.bot.runner.bot.operation.def

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import java.util.*

class ReadBotPropertiesOperation : BotOperation("Read bot properties") {

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return ArrayList()
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        return OperationResult.ofObject(true, "Bot properties retrieved", bot.botPropertyStore
            .map { it.key.key to it.value }
            .toMap())
    }
}