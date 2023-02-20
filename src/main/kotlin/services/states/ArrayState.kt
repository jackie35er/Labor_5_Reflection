package services.states

import services.ReflectionUtils.writeTag
import services.XmlSerializer

class ArrayState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState {


    override fun serialize(any: Any) {
        val array = any as Array<*>
        array.forEach { element ->
            xmlSerializer.writeTag("value", element!!)
        }
    }


}