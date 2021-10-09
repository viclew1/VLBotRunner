package fr.lewon.bot.runner.bot.props

import java.util.*

class BotPropertyStore : HashMap<BotPropertyDescriptor, Any?>() {

    fun getByKey(key: String): Any? {
        return this[this.keys.stream()
            .filter { p -> p.key == key }
            .findFirst().orElse(null)]
    }

    fun containsByKey(key: String): Boolean {
        return this.keys.stream()
            .filter { p -> p.key == key }
            .findFirst().orElse(null) != null;
    }

    companion object {
        private const val serialVersionUID = 8101778657485578888L
    }

}
