package fr.lewon.bot.runner.bot.logs

/**
 * The available log levels that can be used in a Bot.
 */
enum class LogLevel(upperLevel: LogLevel? = null) {
    /** Turns of the logging  */
    OFF,
    /** Designates severe errors leading to an abort  */
    FATAL,
    /** Designates errors that won't lead to an abort  */
    ERROR(FATAL),
    /** Designates potentially dangerous situations  */
    WARN(ERROR),
    /** Designates information about the progress of the process  */
    INFO(WARN),
    /** Designates information useful to debug the application  */
    DEBUG(INFO),
    /** Designates any spamming information that you wouldn't want to see in any other level  */
    TRACE(DEBUG);

    val enclosedLevels: Array<LogLevel> = arrayOf(
            *(upperLevel?.enclosedLevels ?: emptyArray()),
            this)
}