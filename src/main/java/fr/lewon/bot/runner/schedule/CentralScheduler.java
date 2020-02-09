package fr.lewon.bot.runner.schedule;

import com.sun.jdi.AbsentInformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CentralScheduler {

    @Autowired
    private ScheduledAnnotationBeanPostProcessor processor;

    public void cancelTaskAutoExecution(BotTask task) {
        this.processor.postProcessBeforeDestruction(task, null);
    }

    public void startTaskAutoExecution(BotTask task) {
        this.processor.postProcessAfterInitialization(task, null);
    }

    @Bean
    public AbsentInformationException unTruc() {
        this.startTaskAutoExecution(new Task1());
        this.startTaskAutoExecution(new Task1());
        this.startTaskAutoExecution(new Task1());
        return null;
    }
}
