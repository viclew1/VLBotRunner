package fr.lewon.bot.runner.bot.props

class BotPropertyDescriptor(val key: String, val type: BotPropertyType, val defaultValue: Any?, val description: String, val isNeeded: Boolean, val isNullable: Boolean, val acceptedValues: List<Any?>) {

    constructor(key: String, type: BotPropertyType, defaultValue: Any?, description: String, isNeeded: Boolean, isNullable: Boolean, vararg acceptedValues: Any?) :
            this(key, type, defaultValue, description, isNeeded, isNullable, listOf(*acceptedValues))

    override fun toString(): String {
        return "BotPropertyDescriptor(key='$key', type=$type, defaultValue=$defaultValue, description='$description', isNeeded=$isNeeded, isNullable=$isNullable, acceptedValues=$acceptedValues)"
    }

}
