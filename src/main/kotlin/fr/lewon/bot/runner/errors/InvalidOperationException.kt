package fr.lewon.bot.runner.errors

class InvalidOperationException(operation: String, initialState: String) :
    Exception("Invalid operation, [$operation] can't be applied on the state [$initialState]")