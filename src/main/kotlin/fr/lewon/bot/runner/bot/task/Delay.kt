package fr.lewon.bot.runner.bot.task

import java.util.concurrent.TimeUnit

class Delay(private val amount: Long, private val timeUnit: TimeUnit = TimeUnit.MILLISECONDS) {

    fun getDelayMillis(): Long {
        return this.timeUnit.toMillis(this.amount);
    }

    companion object {
        val NEVER = Delay(-1)
        val ZERO = Delay(0)
    }
}
