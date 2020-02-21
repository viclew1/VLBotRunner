package fr.lewon.bot.runner

import fr.lewon.bot.runner.bot.AbstractBotBuilder
import fr.lewon.bot.runner.bot.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.def.ReadBotPropertiesOperation
import fr.lewon.bot.runner.bot.operation.def.UpdateBotPropertiesOperation
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyType
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import fr.lewon.bot.runner.util.BotOperationRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.web.reactive.function.client.WebClient
import kotlin.random.Random

@SpringBootApplication
@PropertySource("appli.properties")
open class App : ApplicationRunner {

    @Autowired
    private lateinit var botOperationRunner: BotOperationRunner

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        val params = HashMap<String, String?>()
        params["bool1"] = "true"
        params["lol"] = "abc"
        val bot = BotBuilderExample().buildBot(params)
        bot.start()
        println("BOT STARTED")
        Thread {
            try {
                Thread.sleep(5000)
                println(this.botOperationRunner.runOperation(bot.botOperations[1], bot, HashMap()))
                val props = HashMap<String, String?>()
                props["int1"] = "654"
                props["bool1"] = null
                props["lol"] = "abc"
                Thread.sleep(5000)
                println(this.botOperationRunner.runOperation(bot.botOperations[0], bot, props))
                Thread.sleep(5000)
                bot.stop()
                println("BOT STOPPED")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(App::class.java)
        }
    }
}

internal class BotBuilderExample : AbstractBotBuilder("botNameExample",
        BotPropertyDescriptor("bool1", BotPropertyType.BOOLEAN, false, "boolean property", true, true, true, false),
        BotPropertyDescriptor("int1", BotPropertyType.INTEGER, -1, "integer property", isNeeded = false, isNullable = false)) {

    override fun getBotOperations(): List<BotOperation> {
        return listOf(UpdateBotPropertiesOperation(),
                ReadBotPropertiesOperation())
    }

    override fun buildWebClient(): WebClient {
        return WebClient.create();
    }

    override fun getInitialTasks(bot: Bot): List<BotTask> {
        val t = TaskExample(bot)
        return listOf(
                t,
                TaskDisplayTimers(bot, t))
    }
}

internal class TaskDisplayTimers(bot: Bot, private val task: TaskExample) : BotTask(bot) {

    override fun getLabel(): String {
        return "Timers    "
    }

    @Throws(Exception::class)
    override fun doExecute(bot: Bot): TaskResult {
        this.task.executionTimeMillis?.let {
            print("${System.currentTimeMillis() - it} / ${this.task.delay}                     " + "\r")
        }
        return TaskResult(Delay(10))
    }
}

internal class TaskExample(bot: Bot) : BotTask(bot) {

    var delay: Long = 0
        private set

    override fun getLabel(): String {
        return "Task " + this.delay
    }

    @Throws(Exception::class)
    override fun doExecute(bot: Bot): TaskResult {
        this.delay = Random.nextInt(5000).toLong()
        return TaskResult(Delay(this.delay))
    }
}