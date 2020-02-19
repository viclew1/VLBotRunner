package fr.lewon.bot.runner.lifecycle.task

import fr.lewon.bot.runner.lifecycle.ILifeCycleOperation
import fr.lewon.bot.runner.lifecycle.Transition

enum class TaskLifeCycleOperation constructor(vararg possibleTransitions: Transition<TaskState>) : ILifeCycleOperation<TaskState> {

    START(Transition<TaskState>(TaskState.ACTIVE, TaskState.PENDING)),
    STOP(Transition<TaskState>(TaskState.DISPOSED, TaskState.ACTIVE));

    override val transitions: List<Transition<TaskState>> = listOf(*possibleTransitions);
}
