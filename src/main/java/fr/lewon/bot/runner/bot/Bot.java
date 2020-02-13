package fr.lewon.bot.runner.bot;

import fr.lewon.bot.runner.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.errors.InvalidOperationException;
import fr.lewon.bot.runner.lifecycle.bot.BotOperation;
import fr.lewon.bot.runner.lifecycle.bot.BotState;
import fr.lewon.bot.runner.schedule.BotTask;
import fr.lewon.bot.runner.util.BeanUtil;
import fr.lewon.bot.runner.util.BotTaskScheduler;

import java.util.ArrayList;
import java.util.List;

public abstract class Bot {

    private static final BotTaskScheduler botTaskScheduler = BeanUtil.getBean(BotTaskScheduler.class);

    private List<BotTask> tasks = new ArrayList<>();
    private BotState state = BotState.PENDING;
    private BotPropertyStore botPropertyStore;

    public Bot(BotPropertyStore botPropertyStore) {
        this.botPropertyStore = botPropertyStore;
    }

    public void start() throws InvalidOperationException {
        this.state = BotOperation.START.getResultingState(this.state);
        this.tasks.addAll(this.getInitialTasks());
        botTaskScheduler.startTaskAutoExecution(this.tasks);
    }

    public void stop() throws InvalidOperationException {
        this.state = BotOperation.STOP.getResultingState(this.state);
        botTaskScheduler.cancelTaskAutoExecution(this.tasks);
        this.tasks.clear();
    }

    public void togglePause(boolean isPaused) throws InvalidOperationException {
        this.state = BotOperation.TOGGLE_PAUSE.getResultingState(this.state);
    }

    public void kill() throws InvalidOperationException {
        this.state = BotOperation.KILL.getResultingState(this.state);
    }

    public List<BotTask> getTasks() {
        return new ArrayList<>(this.tasks);
    }

    public BotPropertyStore getBotPropertyStore() {
        return this.botPropertyStore;
    }


    protected abstract List<BotTask> getInitialTasks();

}
