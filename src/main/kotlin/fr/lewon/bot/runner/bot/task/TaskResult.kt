package fr.lewon.bot.runner.bot.task

class TaskResult @JvmOverloads constructor(val delay: Delay, val tasksToCreate: List<BotTask> = listOf()) {

    constructor(tasksToCreate: List<BotTask>) : this(Delay.NEVER, tasksToCreate)

    constructor() : this(ArrayList())
}
