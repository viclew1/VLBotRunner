package fr.lewon.bot.runner.lifecycle.bot

import fr.lewon.bot.runner.lifecycle.ILifeCycleOperation
import fr.lewon.bot.runner.lifecycle.bot.BotState.*

enum class BotLifeCycleOperation(private val to: BotState, private vararg val from: BotState) : ILifeCycleOperation<BotState> {

    START(ACTIVE, PENDING, STOPPED),
    STOP(STOPPED, ACTIVE),
    CRASH(CRASHED);

    override fun getTo(): BotState {
        return to
    }

    override fun getFrom(): List<BotState> {
        return listOf(*from)
    }
}