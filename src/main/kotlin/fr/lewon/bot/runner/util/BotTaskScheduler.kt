package fr.lewon.bot.runner.util

import fr.lewon.bot.runner.bot.task.BotTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.util.concurrent.ScheduledFuture

@Component
class BotTaskScheduler {

    @Autowired
    private lateinit var scheduler: TaskScheduler

    private val jobsMap: MutableMap<BotTask, ScheduledFuture<*>> = HashMap()

    val tasks: List<BotTask>
        get() = ArrayList(this.jobsMap.keys)

    fun cancelTaskAutoExecution(task: BotTask) {
        this.jobsMap.remove(task)?.cancel(true)
    }

    fun cancelTaskAutoExecution(tasks: List<BotTask>) {
        tasks.forEach { t -> this.cancelTaskAutoExecution(t) }
    }

    fun startTaskAutoExecution(task: BotTask) {
        this.scheduler.schedule(task, task)?.let { this.jobsMap[task] = it }
    }

    fun startTaskAutoExecution(tasks: List<BotTask>) {
        tasks.forEach { t -> this.startTaskAutoExecution(t) }
    }
}
