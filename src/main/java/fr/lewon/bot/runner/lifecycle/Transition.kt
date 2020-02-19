package fr.lewon.bot.runner.lifecycle

class Transition<T>(val toState: T, val fromStates: List<T>) {
    constructor(toState: T, vararg fromStates: T) : this(toState, listOf(*fromStates))
}