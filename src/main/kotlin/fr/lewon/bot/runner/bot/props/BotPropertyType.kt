package fr.lewon.bot.runner.bot.props

enum class BotPropertyType private constructor(private val typeChecker: (String) -> Any) {

    INTEGER({ v -> v.toInt() }),
    STRING({ v -> v }),
    FLOAT({ v -> v.toFloat() }),
    BOOLEAN({ v -> v.toBoolean() });

    @Throws(Exception::class)
    fun parse(value: String): Any {
        return this.typeChecker.invoke(value)
    }
}