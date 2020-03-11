package fr.lewon.bot.runner.errors

import fr.lewon.bot.runner.bot.operation.BotOperation

class BotOperationExecutionException(botOperation: BotOperation, message: String? = null, cause: Exception? = null) :
        Exception("An error occurred when running [" + botOperation.javaClass.canonicalName + "] : $message", cause)
