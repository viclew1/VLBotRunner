package fr.lewon.bot.runner.errors

import fr.lewon.bot.runner.bot.operation.BotOperation

class BotOperationExecutionException(botOperation: BotOperation, cause: Exception) : Exception("An error occurred when running [" + botOperation.javaClass.getCanonicalName() + "]", cause) {
    companion object {
        private const val serialVersionUID = 8125206445264588526L
    }
}
