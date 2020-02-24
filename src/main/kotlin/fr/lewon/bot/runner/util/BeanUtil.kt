package fr.lewon.bot.runner.util

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class BeanUtil private constructor() : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    companion object {

        private lateinit var context: ApplicationContext

        inline fun <reified T : Any> getBean(): T {
            return getBean(T::class)
        }

        fun <T : Any> getBean(beanClass: KClass<T>): T {
            return getBean(beanClass.java)
        }

        fun <T : Any> getBean(beanClass: Class<T>): T {
            return context.getBean(beanClass)
        }

    }
}
