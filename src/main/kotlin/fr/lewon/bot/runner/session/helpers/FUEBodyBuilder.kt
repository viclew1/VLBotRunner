package fr.lewon.bot.runner.session.helpers

import com.fasterxml.jackson.annotation.JsonProperty
import java.lang.reflect.Field
import java.net.URLEncoder

/**
 * Stands for form url encoded. Designs a body that's built the same way as query string parameters, being : <br></br>
 * first_param=first_value&second_param=second_value
 */
class FUEBodyBuilder {
    /**
     * Generates a body designed to be used in an HTTP request. Bases itself on the [com.fasterxml.jackson.annotation.JsonProperty] annotations found
     * in the class of the passed object. Refer to these to see how they work.
     *
     * @param toWrite
     * @return
     */
    fun generateBody(toWrite: Any, encoding: String = "UTF-8"): String {
        val fieldsToWrite = getBodyElements(toWrite)
        val it = fieldsToWrite.iterator()
        val body = StringBuilder()
        while (it.hasNext()) {
            val element = it.next()
            val label = element.label
            val value = element.value
            if ("" == value) {
                continue
            }
            body.append("${URLEncoder.encode(label, encoding)}=$value")
            if (it.hasNext()) {
                body.append("&")
            }
        }
        return body.toString()
    }

    private fun getBodyElements(toWrite: Any): List<BodyElement> {
        val refClass: Class<*> = toWrite.javaClass
        val allFields = getAllDeclaredFields(refClass)
        val elements: MutableList<BodyElement> = ArrayList()
        for (f in allFields) {
            val bodyMember = f.getDeclaredAnnotation(JsonProperty::class.java) ?: continue
            val elementName = getElementName(f, bodyMember)
            val elementValue = getElementValue(f, toWrite)
            if (elementValue != null) {
                addBodyElement(elements, elementName, elementValue)
            }
        }
        return elements
    }

    private fun addBodyElement(elements: MutableList<BodyElement>, elementName: String, elementValue: Any) {
        if (MutableCollection::class.java.isInstance(elementValue)) {
            val elementValCollec = elementValue as Collection<*>
            for (elem in elementValCollec) {
                elements.add(BodyElement(elementName, elem.toString()))
            }
        } else {
            elements.add(BodyElement(elementName, elementValue.toString()))
        }
    }

    private fun getElementName(field: Field, bodyMember: JsonProperty): String {
        return if ("" == bodyMember.value) {
            field.name
        } else bodyMember.value
    }

    private fun getElementValue(field: Field, refObj: Any): Any? {
        return try {
            val accessible = field.isAccessible
            field.isAccessible = true
            val `val` = field[refObj]
            field.isAccessible = accessible
            `val`
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: IllegalAccessException) {
            null
        }
    }

    private fun getAllDeclaredFields(refClass: Class<*>): List<Field> {
        val fields: MutableList<Field> = ArrayList()
        if (refClass.superclass != null) {
            fields.addAll(getAllDeclaredFields(refClass.superclass))
        }
        for (f in refClass.declaredFields) {
            fields.add(f)
        }
        return fields
    }

    private inner class BodyElement internal constructor(val label: String, val value: String)
}