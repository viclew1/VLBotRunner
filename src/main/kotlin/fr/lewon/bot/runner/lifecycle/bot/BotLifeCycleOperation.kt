package fr.lewon.bot.runner.lifecycle.bot

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.errors.InvalidOperationException

enum class BotLifeCycleOperation(private val operation: (Bot) -> Unit) {

    START({ it.start() }),
    STOP({ it.stop() });

    fun run(bot: Bot) {
        if (!bot.state.operations.contains(this)) {
            throw InvalidOperationException(name, bot.state.name)
        }
        val oldState = bot.state
        operation.invoke(bot)
        bot.logger.info("Bot transition applied : [$oldState] => [${bot.state}]")
    }

}