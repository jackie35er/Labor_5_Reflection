package services.states

import services.ReflectionUtils
import services.XmlSerializer

class CollectionState(
    val xmlSerializer: XmlSerializer
) : XmlSerializerState {
    override fun serialize(any: Any) {
        any as Collection<*>
        any.forEach {
            xmlSerializer.xmlWriter.open("value")
            if (ReflectionUtils.isPrimitive(it!!::class)) {
                xmlSerializer.xmlWriter.write(it.toString())
            } else {
                xmlSerializer.setState(ClassState(xmlSerializer))
                xmlSerializer.serialize(it)
            }
            xmlSerializer.xmlWriter.close("value")
        }


    }
}