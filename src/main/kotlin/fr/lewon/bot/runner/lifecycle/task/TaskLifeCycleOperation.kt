package fr.lewon.bot.runner.lifecycle.task

import fr.lewon.bot.runner.lifecycle.ILifeCycleOperation

enum class TaskLifeCycleOperation constructor(private val to: TaskState, private vararg val from: TaskState) : ILifeCycleOperation<TaskState> {

    START(TaskState.ACTIVE, TaskState.PENDING),
    STOP(TaskState.DISPOSED, TaskState.ACTIVE),
    CRASH(TaskState.CRASHED);

    override fun getTo(): TaskState {
        return to
    }

    override fun getFrom(): List<TaskState> {
        return listOf(*from)
    }

}
