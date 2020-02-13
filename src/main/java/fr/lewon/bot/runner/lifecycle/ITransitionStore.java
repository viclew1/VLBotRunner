package fr.lewon.bot.runner.lifecycle;

public interface ITransitionStore<T> {

    Transition<T>[] getTransitions();

}
