package fr.lewon.bot.runner.util

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.errors.BotOperationExecutionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BotOperationRunner {

    @Autowired
    private lateinit var botPropertyParser: BotPropertyParser

    fun runOperation(botOperation: BotOperation, bot: Bot, params: Map<String, String?>): OperationResult {

        val paramStore = this.botPropertyParser.parseParams(params, botOperation.getNeededProperties(bot))
        try {
            return botOperation.run(bot, paramStore)
        } catch (e: Exception) {
            throw BotOperationExecutionException(botOperation, cause = e, message = e.message)
        }

    }

}
