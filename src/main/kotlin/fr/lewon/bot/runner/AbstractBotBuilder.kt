package fr.lewon.bot.runner

import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.props.BotPropertyType
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.session.AbstractSessionManager
import fr.lewon.bot.runner.util.BotPropertyParser

abstract class AbstractBotBuilder(
    val expectedLoginProperties: List<BotPropertyDescriptor>,
    botPropertyDescriptors: List<BotPropertyDescriptor>,
    val botOperations: List<BotOperation> = emptyList()
) {

    val botPropertyDescriptors: List<BotPropertyDescriptor> = listOf(
        BotPropertyDescriptor(
            key = "auto_restart_timer",
            type = BotPropertyType.INTEGER,
            defaultValue = null,
            description = "Amount of minutes before restarting a bot on crash. If null, the bot doesn't restart",
            isNeeded = false,
            isNullable = true
        ),
        *botPropertyDescriptors.toTypedArray()
    )

    fun buildBot(login: String, loginProperties: Map<String, String>, properties: Map<String, String?>): Bot {
        val loginPropertyStore = BotPropertyParser.parseParams(loginProperties, this.expectedLoginProperties)
        val botPropertyStore = BotPropertyParser.parseParams(properties, this.botPropertyDescriptors)
        return Bot(botPropertyStore, { b -> this.getInitialTasks(b) }, buildSessionManager(login, loginPropertyStore))
    }

    protected abstract fun buildSessionManager(
        login: String,
        loginPropertyStore: BotPropertyStore
    ): AbstractSessionManager

    protected abstract fun getInitialTasks(bot: Bot): List<BotTask>

}
