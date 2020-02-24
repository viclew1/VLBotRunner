package fr.lewon.bot.runner.bot.task

class TaskResult @JvmOverloads constructor(val delay: Delay = Delay.NEVER, val tasksToCreate: List<BotTask> = listOf())