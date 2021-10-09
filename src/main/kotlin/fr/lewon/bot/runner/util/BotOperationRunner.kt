package fr.lewon.bot.runner.util

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BotOperationRunner {

    @Autowired
    private lateinit var botPropertyParser: BotPropertyParser

    fun runOperation(botOperation: BotOperation, bot: Bot, params: Map<String, String?>): OperationResult {

        val paramStore = this.botPropertyParser.parseParams(params, botOperation.getNeededProperties(bot))
        return try {
            botOperation.run(bot, paramStore)
        } catch (e: Exception) {
            val error = e.message ?: "Unknown error"
            bot.logger.error("Failed to run operation [${botOperation.label}] : $error")
            OperationResult(false, error)
        }

    }

}
