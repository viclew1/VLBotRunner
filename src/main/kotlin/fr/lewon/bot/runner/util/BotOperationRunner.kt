package fr.lewon.bot.runner.util

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult

object BotOperationRunner {

    fun runOperation(botOperation: BotOperation, bot: Bot, params: Map<String, String?>): OperationResult {

        val paramStore = BotPropertyParser.parseParams(params, botOperation.getNeededProperties(bot))
        return try {
            botOperation.run(bot, paramStore)
        } catch (e: Exception) {
            val error = e.message ?: "Unknown error"
            bot.logger.error("Failed to run operation [${botOperation.label}] : $error")
            OperationResult(false, error)
        }

    }

}
