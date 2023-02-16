package services.states

import services.ReflectionUtils
import services.XmlSerializer

class MapState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState {
    override fun serialize(any: Any) {
        val map = any as Map<*, *>
        map.forEach { (key, value) ->
            xmlSerializer.xmlWriter.open("key")
            if (ReflectionUtils.isPrimitive(key!!::class)) {
                xmlSerializer.xmlWriter.write(key.toString())
            } else {
                xmlSerializer.setState(ClassState(xmlSerializer))
                xmlSerializer.serialize(key)
            }
            xmlSerializer.xmlWriter.close("key")

            xmlSerializer.xmlWriter.open("value")
            if (ReflectionUtils.isPrimitive(value!!::class)) {
                xmlSerializer.xmlWriter.write(value.toString())
            } else {
                xmlSerializer.setState(ClassState(xmlSerializer))
                xmlSerializer.serialize(value)
            }
            xmlSerializer.xmlWriter.close("value")
        }
    }
}