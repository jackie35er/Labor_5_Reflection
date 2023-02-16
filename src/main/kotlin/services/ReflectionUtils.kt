package services

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
}