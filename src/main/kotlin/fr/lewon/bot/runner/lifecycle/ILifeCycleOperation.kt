package fr.lewon.bot.runner.lifecycle

import fr.lewon.bot.runner.errors.InvalidOperationException

interface ILifeCycleOperation<T> {

    fun getTo(): T

    fun getFrom(): List<T>

    @Throws(InvalidOperationException::class)
    fun getResultingState(initialState: T): T {
        if (getFrom().isEmpty() || getFrom().contains(initialState)) {
            return getTo()
        }
        throw InvalidOperationException(this, initialState.toString())
    }

}
