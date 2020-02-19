package fr.lewon.bot.runner.errors

import fr.lewon.bot.runner.lifecycle.ILifeCycleOperation

class InvalidOperationException(operation: ILifeCycleOperation<*>, initialState: String) : Exception("Invalid operation, [$operation] can't be applied on the state [$initialState]") {
    companion object {
        private const val serialVersionUID = -7690694602881356242L
    }
}
