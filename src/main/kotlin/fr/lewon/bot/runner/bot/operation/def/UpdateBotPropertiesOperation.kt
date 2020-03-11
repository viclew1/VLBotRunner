package fr.lewon.bot.runner.bot.operation.def

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import kotlin.streams.toList

class UpdateBotPropertiesOperation : BotOperation("Update bot properties") {

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return bot.botPropertyStore.entries.stream()
                .map { bpd ->
                    BotPropertyDescriptor(
                            bpd.key.key,
                            bpd.key.type,
                            bpd.value,
                            bpd.key.description,
                            false,
                            bpd.key.isNullable,
                            bpd.key.acceptedValues)
                }
                .toList()
    }

    @Throws(Exception::class)
    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        for (e in bot.botPropertyStore.entries) {
            if (paramsPropertyStore.containsByKey(e.key.key)) {
                e.setValue(paramsPropertyStore.getByKey(e.key.key))
            }
        }
        return OperationResult(true, "Bot properties updated", bot.botPropertyStore
                .map { it.key.key to it.value }
                .toMap())
    }
}
