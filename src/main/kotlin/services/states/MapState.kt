package services.states

import services.ReflectionUtils.writeTag
import services.XmlSerializer

class MapState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState {
    override fun serialize(any: Any) {
        val map = any as Map<*, *>
        map.forEach { (key, value) ->
            xmlSerializer.writeTag("key", key!!)
            xmlSerializer.writeTag("value", value!!)
        }
    }
}