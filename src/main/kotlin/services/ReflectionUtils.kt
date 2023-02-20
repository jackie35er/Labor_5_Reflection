package services

import services.states.ClassState
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

object ReflectionUtils {

    fun isPrimitive(clazz: KClass<*>): Boolean {
        return clazz == Int::class ||
                clazz == Long::class ||
                clazz == Double::class ||
                clazz == Float::class ||
                clazz == Boolean::class ||
                clazz == Char::class ||
                clazz == String::class
    }

    fun isArray(clazz: KClass<*>): Boolean {
        return clazz.java.isArray
    }

    fun isMap(clazz: KClass<*>): Boolean {
        return clazz.isSubclassOf(Map::class)
    }

    fun isCollection(clazz: KClass<*>): Boolean {
        return clazz.isSubclassOf(Collection::class)
    }

    fun XmlSerializer.writeTag(tagName: String, any: Any) {
        this.xmlWriter.open(tagName)
        if (isPrimitive(any::class)) {
            this.xmlWriter.write(any.toString())
        } else {
            this.setState(ClassState(this))
            this.serialize(any)
        }
        this.xmlWriter.close(tagName)
    }
}