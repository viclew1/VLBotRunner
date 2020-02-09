package fr.lewon.bot.runner.schedule;

public class Task2 extends BotTask {

    @Override
    protected Long doExecute() {
        System.out.println("2");
        return 2000l;
    }
}
