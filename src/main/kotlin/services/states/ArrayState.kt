package services.states

import services.ReflectionUtils
import services.XmlSerializer

class ArrayState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState {


    override fun serialize(any: Any) {
        val array = any as Array<*>
        array.forEach { element ->
            if (ReflectionUtils.isPrimitive(element!!::class)) {
                xmlSerializer.xmlWriter.open("value")
                xmlSerializer.xmlWriter.write(element.toString())
                xmlSerializer.xmlWriter.close("value")
            } else {
                xmlSerializer.setState(ClassState(xmlSerializer))
                xmlSerializer.serialize(element)
            }
        }
    }


}