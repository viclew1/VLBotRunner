package fr.lewon.bot.runner.schedule;

import java.util.concurrent.TimeUnit;

public class Delay {

    public static final Delay ZERO = new Delay(0);

    private final long amount;
    private final TimeUnit timeUnit;

    public Delay(long amount) {
        this(amount, TimeUnit.MILLISECONDS);
    }

    public Delay(long amount, TimeUnit timeUnit) {
        this.amount = amount;
        this.timeUnit = timeUnit;
    }

    public long getDelayMillis() {
        return this.timeUnit.toMillis(this.amount);
    }
}
