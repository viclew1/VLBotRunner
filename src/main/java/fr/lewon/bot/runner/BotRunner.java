package fr.lewon.bot.runner;

import fr.lewon.bot.runner.schedule.BotTask;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotRunner {

    private List<BotTask> tasks = new ArrayList<>();

}
