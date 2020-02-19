package fr.lewon.bot.runner.bot

import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException
import fr.lewon.bot.runner.errors.MissingBotPropertyException
import fr.lewon.bot.runner.util.BeanUtil
import fr.lewon.bot.runner.util.BotPropertyParser

abstract class AbstractBotBuilder(private val botName: String, private val botPropertyDescriptors: List<BotPropertyDescriptor>) {

    protected abstract val botOperations: List<BotOperation>

    constructor(botName: String, vararg botPropertyDescriptors: BotPropertyDescriptor) : this(botName, listOf(*botPropertyDescriptors)) {}

    @Throws(InvalidBotPropertyValueException::class, MissingBotPropertyException::class)
    fun buildBot(properties: Map<String, String?>): Bot {
        val botPropertyStore = botPropertyParser.parseParams(properties, this.botPropertyDescriptors)
        return Bot(botPropertyStore, { b -> this.getInitialTasks(b) }, this.botOperations)
    }

    protected abstract fun getInitialTasks(bot: Bot): List<BotTask>

    companion object {
        private val botPropertyParser = BeanUtil.getBean(BotPropertyParser::class.java)
    }

}
