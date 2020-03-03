package fr.lewon.bot.runner.bot.logs

import org.slf4j.LoggerFactory
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import kotlin.collections.ArrayList

/**
 * A logger whose goal is to store the logs associated to an [AbstractBot] execution. Offers the following functionalities : <br></br>
 *
 *  * Defining a size determining the amount of logs that can be stored in this logger
 *  * Log by [level][LogLevel] of trace importance
 *
 */
class BotLogger @JvmOverloads constructor(private val maxAge: Long = DEFAULT_MAX_AGE, private val dateFormat: String = DEFAULT_DATE_FORMAT) {

    private val logs: MutableList<Log> = ArrayList()

    /**
     * @return A copy of the stored logs
     */
    fun getLogs(): List<String> {
        return logs.stream()
                .map { log -> log.message }
                .collect(Collectors.toList())
    }

    /**
     * Writes a log in the log store. The passed message will be removed from the store when its max age is reached
     * and will be formatted using the passed level, the current date (formatted using the logger format, or the
     * default one : {@value #DEFAULT_DATE_FORMAT}). <br></br>
     *
     * @param level
     * @param message
     */
    fun log(level: LogLevel, message: String) {
        val now = Date()
        val sdf = SimpleDateFormat(dateFormat)
        val nowStr = sdf.format(now)
        LOGGER.debug(message)
        val currentTime = now.time
        val log = Log("$nowStr : $level - $message", currentTime, level)
        logs.add(log)
        logs.removeIf { l -> l.time + maxAge < currentTime }
    }

    private fun logThrowable(level: LogLevel, message: String, throwable: Throwable) {
        val sw = StringWriter()
        throwable.printStackTrace(PrintWriter(sw))
        log(level, "$message - $sw")
    }

    /**
     * Logs a message with the level [LogLevel.INFO]. See [.log]
     *
     * @param message
     */
    fun info(message: String) {
        log(LogLevel.INFO, message)
    }

    /**
     * Logs a message with the level [LogLevel.INFO] followed by the message contained in the passed throwable. See [.info]
     *
     * @param message
     * @param throwable
     */
    fun info(message: String, throwable: Throwable) {
        logThrowable(LogLevel.INFO, message, throwable)
    }

    /**
     * Logs a message with the level [LogLevel.ERROR]. See [.log]
     *
     * @param message
     */
    fun error(message: String) {
        log(LogLevel.ERROR, message)
    }

    /**
     * Logs a message with the level [LogLevel.ERROR] followed by the message contained in the passed throwable. See [.error]
     *
     * @param message
     * @param throwable
     */
    fun error(message: String, throwable: Throwable) {
        logThrowable(LogLevel.ERROR, message, throwable)
    }

    /**
     * Logs a message with the level [LogLevel.WARN]. See [.log]
     *
     * @param message
     */
    fun warn(message: String) {
        log(LogLevel.WARN, message)
    }

    /**
     * Logs a message with the level [LogLevel.WARN] followed by the message contained in the passed throwable. See [.warn]
     *
     * @param message
     * @param throwable
     */
    fun warn(message: String, throwable: Throwable) {
        logThrowable(LogLevel.WARN, message, throwable)
    }

    /**
     * Logs a message with the level [LogLevel.DEBUG]. See [.log]
     *
     * @param message
     */
    fun debug(message: String) {
        log(LogLevel.DEBUG, message)
    }

    /**
     * Logs a message with the level [LogLevel.DEBUG] followed by the message contained in the passed throwable. See [.debug]
     *
     * @param message
     * @param throwable
     */
    fun debug(message: String, throwable: Throwable) {
        logThrowable(LogLevel.DEBUG, message, throwable)
    }

    /**
     * Logs a message with the level [LogLevel.TRACE]. See [.log]
     *
     * @param message
     */
    fun trace(message: String) {
        log(LogLevel.TRACE, message)
    }

    /**
     * Logs a message with the level [LogLevel.TRACE] followed by the message contained in the passed throwable. See [.trace]
     *
     * @param message
     * @param throwable
     */
    fun trace(message: String, throwable: Throwable) {
        logThrowable(LogLevel.TRACE, message, throwable)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(BotLogger::class.java)
        private val DEFAULT_MAX_AGE: Long = TimeUnit.DAYS.toMillis(1L)
        private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS"
    }

}