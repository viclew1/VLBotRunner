package fr.lewon.bot.runner;

import fr.lewon.bot.runner.bot.AbstractBotBuilder;
import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;
import fr.lewon.bot.runner.errors.InvalidOperationException;
import fr.lewon.bot.runner.schedule.BotTask;
import fr.lewon.bot.runner.schedule.Delay;
import fr.lewon.bot.runner.schedule.TaskResult;
import fr.lewon.bot.runner.util.BeanUtil;
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
                Thread.sleep(10000);
                bot.togglePause(true);
                System.out.println("BOT PAUSED");
                Thread.sleep(10000);
                bot.togglePause(false);
                System.out.println("BOT RESUMED");
                Thread.sleep(10000);
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
        return Arrays.asList(
                new TaskExample());
    }
}

class TaskDisplayTimers extends BotTask {

    @Override
    public String getLabel() {
        return "Timers    ";
    }

    @Override
    protected TaskResult doExecute() throws Exception {
        System.out.println("---------");
        for (BotTask t : BeanUtil.getBean(BotTaskScheduler.class).getTasks()) {
            System.out.println(t.getLabel() + "\t - " + t.getMillisUntilExec());
        }
        System.out.println("---------");
        return new TaskResult(new Delay(1000));
    }
}

class TaskExample extends BotTask {

    private long delay = 0;

    public TaskExample() {
        new Thread(() -> {

        }).start();
    }

    @Override
    public String getLabel() {
        return "Task " + this.delay;
    }

    @Override
    protected TaskResult doExecute() throws Exception {
        this.delay = new Random().nextInt(5000);
        return new TaskResult(new Delay(this.delay));
    }
}