package fr.lewon.bot.runner.bot.operation.def

import fr.lewon.bot.runner.bot.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore

class RebootBotOperation : BotOperation("Reboot bot") {

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return ArrayList()
    }

    @Throws(Exception::class)
    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        bot.reset()
        return OperationResult(true, "Bot restarted", null)
    }
}
