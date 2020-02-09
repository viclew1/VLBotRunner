package fr.lewon.bot.runner;

import fr.lewon.bot.runner.schedule.CentralScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("appli.properties")
public class App {

    @Autowired
    private CentralScheduler centralScheduler;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

}