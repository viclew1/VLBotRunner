package fr.lewon.bot.runner.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

import java.util.concurrent.Executor

@Configuration
@EnableAsync
@EnableScheduling
open class ScheduleConfig : AsyncConfigurer, SchedulingConfigurer {

    @Value("\${bot.task.scheduler.pool.size:50}")
    private val poolSize: Int = 0

    @Autowired
    private lateinit var threadPoolTaskScheduler: ThreadPoolTaskScheduler

    @Bean
    override fun getAsyncExecutor(): Executor? {
        this.threadPoolTaskScheduler.poolSize = this.poolSize
        return ConcurrentTaskExecutor(this.threadPoolTaskScheduler)
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler? {
        return SimpleAsyncUncaughtExceptionHandler()
    }

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setScheduler(this.threadPoolTaskScheduler)
    }

    @Bean
    open fun getThreadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        return ThreadPoolTaskScheduler()
    }

}
