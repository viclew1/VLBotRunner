package fr.lewon.bot.runner.lifecycle.bot

enum class BotState(vararg val operations: BotLifeCycleOperation) {

    PENDING(BotLifeCycleOperation.START),
    ACTIVE(BotLifeCycleOperation.STOP),
    STOPPED(BotLifeCycleOperation.START),
    CRASHED(BotLifeCycleOperation.STOP, BotLifeCycleOperation.START)

}
