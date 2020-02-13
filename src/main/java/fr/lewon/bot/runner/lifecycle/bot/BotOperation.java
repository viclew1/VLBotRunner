package fr.lewon.bot.runner.lifecycle.bot;

import fr.lewon.bot.runner.lifecycle.IOperation;
import fr.lewon.bot.runner.lifecycle.Transition;

import static fr.lewon.bot.runner.lifecycle.bot.BotState.*;

public enum BotOperation implements IOperation<BotState> {

    START(new Transition<>(ACTIVE, PENDING, STOPPED)),
    STOP(new Transition<>(STOPPED, ACTIVE)),
    KILL(new Transition<>(KILLED, PENDING, ACTIVE, STOPPED));

    private Transition<BotState>[] possibleTransitions;

    BotOperation(Transition... possibleTransitions) {
        this.possibleTransitions = possibleTransitions;
    }

    @Override
    public Transition<BotState>[] getTransitions() {
        return this.possibleTransitions;
    }
}