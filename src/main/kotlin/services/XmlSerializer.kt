package services

import services.states.ClassState
import services.states.XmlSerializerState
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.functions

class XmlSerializer {

    val xmlWriter = XmlWriter()
    private var state: XmlSerializerState = ClassState(this)


    fun serialize(any: Any): String {
        state.serialize(any)
        return xmlWriter.compile()
    }

    fun setState(state: XmlSerializerState) {
        this.state = state
    }

}