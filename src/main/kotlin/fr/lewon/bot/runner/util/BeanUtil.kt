package fr.lewon.bot.runner.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class BeanUtil private constructor() : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    companion object {

        private lateinit var context: ApplicationContext

        inline fun <reified T : Any> getBean(): T {
            return getBean(T::class.java)
        }

        fun <T : Any> getBean(beanClass: Class<T>): T {
            return context.getBean(beanClass)
        }

    }
}
