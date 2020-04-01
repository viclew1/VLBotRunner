package fr.lewon.bot.runner.bot.task.def

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult
import fr.lewon.bot.runner.lifecycle.bot.BotLifeCycleOperation

class RestartBotTask(bot: Bot, initialDelayMillis: Long) : BotTask("Restart bot", bot, initialDelayMillis) {

    override fun doExecute(): TaskResult {
        BotLifeCycleOperation.START.run(bot)
        return TaskResult()
    }
}