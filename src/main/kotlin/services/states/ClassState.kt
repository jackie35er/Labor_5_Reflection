package services.states

import services.XmlSerializer
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties

class ClassState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState{


    override fun serialize(any: Any) {
        val simpleName = any::class.simpleName ?: "Anonymous"
        xmlSerializer.xmlWriter.open(simpleName)
        xmlSerializer.setState(determineState(any))
        any::class.declaredMemberProperties.forEach {

            xmlSerializer.setState(determineState(it))
            xmlSerializer.serialize(any)
        }

        xmlSerializer.serialize(any)
        xmlSerializer.xmlWriter.close(simpleName)
    }

    private fun determineState(property: KProperty<*>): XmlSerializerState {
        return StateFactory.getState(property, xmlSerializer)
    }


}