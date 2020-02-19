package fr.lewon.bot.runner.util

import fr.lewon.bot.runner.bot.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.errors.BotOperationExecutionException
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException
import fr.lewon.bot.runner.errors.MissingBotPropertyException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BotOperationRunner private constructor() {

    @Autowired
    private lateinit var botPropertyParser: BotPropertyParser

    @Throws(InvalidBotPropertyValueException::class, BotOperationExecutionException::class, MissingBotPropertyException::class)
    fun runOperation(botOperation: BotOperation, bot: Bot, params: Map<String, String?>): Any {

        val paramStore = this.botPropertyParser.parseParams(params, botOperation.getNeededProperties(bot))
        try {
            return botOperation.run(bot, paramStore)
        } catch (e: Exception) {
            throw BotOperationExecutionException(botOperation, e)
        }

    }

}
