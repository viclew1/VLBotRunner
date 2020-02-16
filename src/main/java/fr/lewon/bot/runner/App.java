package fr.lewon.bot.runner;

import fr.lewon.bot.runner.bot.AbstractBotBuilder;
import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.bot.task.BotTask;
import fr.lewon.bot.runner.bot.task.Delay;
import fr.lewon.bot.runner.bot.task.TaskResult;
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;
import fr.lewon.bot.runner.errors.InvalidOperationException;
import fr.lewon.bot.runner.util.BotTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.rmi.server.RMIClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@PropertySource("appli.properties")
public class App {

    @Autowired
    private BotTaskScheduler botTaskScheduler;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Bean
    public RMIClassLoader test() throws InvalidBotPropertyValueException, InvalidOperationException, InterruptedException {

        Bot bot = new BotBuilderExample().buildBot(new HashMap<>());
        bot.start();
        System.out.println("BOT STARTED");
        new Thread(() -> {
            try {
                Thread.sleep(30000);
                bot.stop();
                System.out.println("BOT STOPPED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return null;
    }
}

class BotBuilderExample extends AbstractBotBuilder {

    public BotBuilderExample() {
        super("botNameExample");
    }

    @Override
    protected List<BotTask> getInitialTasks() {
        TaskExample t = new TaskExample();
        return Arrays.asList(
                t,
                new TaskDisplayTimers(t));
    }
}

class TaskDisplayTimers extends BotTask {

    private TaskExample task;

    public TaskDisplayTimers(TaskExample task) {
        this.task = task;
    }

    @Override
    public String getLabel() {
        return "Timers    ";
    }

    @Override
    protected TaskResult doExecute() throws Exception {
        if (this.task.getExecutionTimeMillis() != null) {
            System.out.print(System.currentTimeMillis() - this.task.getExecutionTimeMillis() + " / " + this.task.getDelay() + "                     " + "\r");
        }
        return new TaskResult(new Delay(10));
    }
}

class TaskExample extends BotTask {

    private long delay = 0;

    public TaskExample() {
    }

    @Override
    public String getLabel() {
        return "Task " + this.delay;
    }

    @Override
    protected TaskResult doExecute() {
        this.delay = new Random().nextInt(5000);
        return new TaskResult(new Delay(this.delay));
    }

    public long getDelay() {
        return this.delay;
    }
}