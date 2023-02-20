package services.states

import services.ReflectionUtils.writeTag
import services.XmlSerializer

class CollectionState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState {
    override fun serialize(any: Any) {
        any as Collection<*>
        any.forEach {
            xmlSerializer.writeTag("value", it!!)
        }


    }
}