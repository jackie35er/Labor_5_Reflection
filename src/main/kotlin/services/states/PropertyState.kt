package services.states

import services.ReflectionUtils
import services.XmlSerializer
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

class PropertyState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState {
    override fun serialize(any: Any) {
        val properties = any::class.declaredMemberProperties
        properties.forEach { property ->
            property.isAccessible = true

            val value = property.getter.call(any) ?: return@forEach
            println(value::class)
            xmlSerializer.xmlWriter.open(property.name)
            if (ReflectionUtils.isPrimitive(value::class)
            ) {
                xmlSerializer.xmlWriter.write(value.toString())
            } else if (ReflectionUtils.isArray(value::class)) {
                xmlSerializer.setState(ArrayState(xmlSerializer))
                xmlSerializer.serialize(value)
            } else if (ReflectionUtils.isMap(value::class)) {
                xmlSerializer.setState(MapState(xmlSerializer))
                xmlSerializer.serialize(value)
            } else if (ReflectionUtils.isCollection(value::class)) {
                xmlSerializer.setState(CollectionState(xmlSerializer))
                xmlSerializer.serialize(value)
            } else {
                xmlSerializer.setState(ClassState(xmlSerializer))
                xmlSerializer.serialize(value)
            }
            xmlSerializer.xmlWriter.close(property.name)
        }
    }


}


