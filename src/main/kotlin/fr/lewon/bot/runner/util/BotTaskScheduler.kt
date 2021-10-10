package fr.lewon.bot.runner.util

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import java.util.*
import kotlin.collections.HashMap

object BotTaskScheduler {

    private val timerByBot = HashMap<Bot, Timer>()

    fun cancelTaskAutoExecution(bot: Bot) {
        timerByBot.remove(bot)?.cancel()
    }

    fun startTaskAutoExecution(task: BotTask) {
        val timer = timerByBot.computeIfAbsent(task.bot) { Timer() }
        timer.schedule(buildTaskTimer(task), task.initialDelayMillis)
    }

    private fun buildTaskTimer(task: BotTask): TimerTask {
        return object : TimerTask() {
            override fun run() {
                task.run()
                scheduleNextExecution(task)
            }
        }
    }

    private fun scheduleNextExecution(task: BotTask) {
        val nextExecutionDate = task.executionDate
        if (nextExecutionDate != null) {
            val timer = timerByBot[task.bot] ?: error("No timer registered for this bot")
            timer.schedule(buildTaskTimer(task), nextExecutionDate.time - System.currentTimeMillis())
        } else {
            task.bot.tasks.remove(task)
        }

    }

    fun startTaskAutoExecution(tasks: List<BotTask>) {
        tasks.forEach { t -> this.startTaskAutoExecution(t) }
    }
}
