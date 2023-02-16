package services.states

import services.XmlSerializer

class ClassState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState{


    override fun serialize(any: Any) {
        val simpleName = any::class.simpleName ?: "Anonymous"
        xmlSerializer.xmlWriter.open(simpleName)
        xmlSerializer.setState(PropertyState(xmlSerializer))
        xmlSerializer.serialize(any)
        xmlSerializer.xmlWriter.close(simpleName)
    }



}