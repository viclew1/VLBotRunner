package fr.lewon.bot.runner.schedule;

public class Task1 extends BotTask {

    @Override
    protected Long doExecute() {
        System.out.println(this);
        return 1000l;
    }

}
