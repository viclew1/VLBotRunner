package fr.lewon.bot.runner.bot.task.def

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class RestartBotTask(bot: Bot, initialDelayMillis: Long) : BotTask(bot, initialDelayMillis) {

    override fun getLabel(): String {
        return "Restart bot"
    }

    @Throws(Exception::class)
    override fun doExecute(bot: Bot): TaskResult {
        bot.start()
        return TaskResult()
    }
}