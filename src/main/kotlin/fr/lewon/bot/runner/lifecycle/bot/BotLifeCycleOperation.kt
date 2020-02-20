package fr.lewon.bot.runner.lifecycle.bot

import fr.lewon.bot.runner.lifecycle.ILifeCycleOperation
import fr.lewon.bot.runner.lifecycle.Transition

import fr.lewon.bot.runner.lifecycle.bot.BotState.*

enum class BotLifeCycleOperation(vararg possibleTransitions: Transition<BotState>) : ILifeCycleOperation<BotState> {

    START(Transition<BotState>(ACTIVE, PENDING, STOPPED)),
    STOP(Transition<BotState>(STOPPED, ACTIVE)),
    KILL(Transition<BotState>(KILLED, PENDING, ACTIVE, STOPPED));

    override val transitions: List<Transition<BotState>> = listOf(*possibleTransitions)

}