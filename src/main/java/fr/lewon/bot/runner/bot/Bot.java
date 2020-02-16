package fr.lewon.bot.runner.bot;

import fr.lewon.bot.runner.bot.operation.BotOperation;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.bot.task.BotTask;
import fr.lewon.bot.runner.errors.InvalidOperationException;
import fr.lewon.bot.runner.lifecycle.bot.BotLifeCycleOperation;
import fr.lewon.bot.runner.lifecycle.bot.BotState;
import fr.lewon.bot.runner.util.BeanUtil;
import fr.lewon.bot.runner.util.BotTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Bot {

    private static final BotTaskScheduler botTaskScheduler = BeanUtil.getBean(BotTaskScheduler.class);

    private Function<Bot, List<BotTask>> initialTasksGenerator;
    private List<BotTask> tasks = new ArrayList<>();
    private BotState state = BotState.PENDING;
    private BotPropertyStore botPropertyStore;

    public Bot(BotPropertyStore botPropertyStore, Function<Bot, List<BotTask>> initialTasksGenerator) {
        this.botPropertyStore = botPropertyStore;
        this.initialTasksGenerator = initialTasksGenerator;
    }

    public void runOperation(BotOperation botOperation, BotPropertyStore paramsStore) throws Exception {
        botOperation.run(this.getBotPropertyStore(), paramsStore);
    }

    public void start() throws InvalidOperationException {
        this.state = BotLifeCycleOperation.START.getResultingState(this.state);
        this.tasks.addAll(this.initialTasksGenerator.apply(this));
        botTaskScheduler.startTaskAutoExecution(this.tasks);
    }

    public void stop() throws InvalidOperationException {
        this.state = BotLifeCycleOperation.STOP.getResultingState(this.state);
        botTaskScheduler.cancelTaskAutoExecution(this.tasks);
        this.tasks.clear();
    }

    public void kill() throws InvalidOperationException {
        this.state = BotLifeCycleOperation.KILL.getResultingState(this.state);
    }

    public List<BotTask> getTasks() {
        return new ArrayList<>(this.tasks);
    }

    public BotPropertyStore getBotPropertyStore() {
        return this.botPropertyStore;
    }

}
