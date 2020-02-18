package fr.lewon.bot.runner;

import fr.lewon.bot.runner.bot.AbstractBotBuilder;
import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.bot.operation.BotOperation;
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.bot.props.BotPropertyType;
import fr.lewon.bot.runner.bot.task.BotTask;
import fr.lewon.bot.runner.bot.task.Delay;
import fr.lewon.bot.runner.bot.task.TaskResult;
import fr.lewon.bot.runner.errors.InvalidBotPropertyValueException;
import fr.lewon.bot.runner.errors.InvalidOperationException;
import fr.lewon.bot.runner.errors.MissingBotPropertyException;
import fr.lewon.bot.runner.util.BotOperationRunner;
import fr.lewon.bot.runner.util.BotTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.rmi.server.RMIClassLoader;
import java.util.*;

@SpringBootApplication
@PropertySource("appli.properties")
public class App implements ApplicationRunner {

    @Autowired
    private BotTaskScheduler botTaskScheduler;

    @Autowired
    private BotOperationRunner botOperationRunner;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Bot bot = new BotBuilderExample().buildBot(new HashMap<>());
        bot.start();
        System.out.println("BOT STARTED");
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                Map<String, String> props = new HashMap<>();
                System.out.println(this.botOperationRunner.runOperation(bot.getBotOperations().get(0), bot, props));
                Thread.sleep(30000);
                bot.stop();
                System.out.println("BOT STOPPED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

class BotOperationExample extends BotOperation {

    public BotOperationExample() {
        super("Bot operation example");
    }

    @Override
    public List<BotPropertyDescriptor> getNeededProperties(Bot bot) {
        return Arrays.asList(
                new BotPropertyDescriptor("prop1", BotPropertyType.BOOLEAN, false, "property bool", true, true, true, false)
        );
    }

    @Override
    public Object run(Bot bot, BotPropertyStore paramsPropertyStore) throws Exception {
        Object prop1 = paramsPropertyStore.getByKey("prop1");
        return "{prop1=" + prop1 + "}";
    }
}

class BotBuilderExample extends AbstractBotBuilder {

    public BotBuilderExample() {
        super("botNameExample");
    }

    @Override
    protected List<BotOperation> getBotOperations() {
        return Arrays.asList(new BotOperationExample());
    }

    @Override
    protected List<BotTask> getInitialTasks(Bot bot) {
        TaskExample t = new TaskExample(bot);
        return Arrays.asList(
                t,
                new TaskDisplayTimers(bot, t));
    }
}

class TaskDisplayTimers extends BotTask {

    private TaskExample task;

    public TaskDisplayTimers(Bot bot, TaskExample task) {
        super(bot);
        this.task = task;
    }

    @Override
    public String getLabel() {
        return "Timers    ";
    }

    @Override
    protected TaskResult doExecute(Bot bot) throws Exception {
        if (this.task.getExecutionTimeMillis() != null) {
            System.out.print(System.currentTimeMillis() - this.task.getExecutionTimeMillis() + " / " + this.task.getDelay() + "                     " + "\r");
        }
        return new TaskResult(new Delay(10));
    }
}

class TaskExample extends BotTask {

    private long delay = 0;

    public TaskExample(Bot bot) {
        super(bot);
    }

    @Override
    public String getLabel() {
        return "Task " + this.delay;
    }

    @Override
    protected TaskResult doExecute(Bot bot) throws Exception {
        this.delay = new Random().nextInt(5000);
        return new TaskResult(new Delay(this.delay));
    }

    public long getDelay() {
        return this.delay;
    }
}