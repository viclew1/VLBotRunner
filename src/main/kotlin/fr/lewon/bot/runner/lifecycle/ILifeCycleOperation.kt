package fr.lewon.bot.runner.lifecycle

import fr.lewon.bot.runner.errors.InvalidOperationException

interface ILifeCycleOperation<T> {

    val transitions: List<Transition<T>>

    @Throws(InvalidOperationException::class)
    fun getResultingState(initialState: T): T {
        for (transition in this.transitions) {
            if (transition.fromStates.contains(initialState)) {
                return transition.toState
            }
        }
        throw InvalidOperationException(this, initialState.toString())
    }

}
