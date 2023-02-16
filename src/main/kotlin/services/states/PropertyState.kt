package services.states

import services.XmlSerializer
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.*

class PropertyState(
    private val xmlSerializer: XmlSerializer
) : XmlSerializerState {
    override fun serialize(any: Any) {
        val properties = any::class.declaredMemberProperties
        properties.forEach { property ->
            property.isAccessible = true
            println(property.valueParameters)
            println((property.getter.call(any)?: "")::class.isSubclassOf(Number::class))
            val value = property.getter.call(any) ?: return@forEach
            xmlSerializer.xmlWriter.open(property.name)

            if ((property.returnType.jvmErasure.isSubclassOf(Number::class)
                        || property.returnType.jvmErasure.isSubclassOf(Boolean::class)
                        || property.returnType.jvmErasure.isSubclassOf(Char::class)
                        )
            ) {
                xmlSerializer.xmlWriter.write(value.toString())
            }
            else if(property.returnType.jvmErasure.isSubclassOf(String::class)) {
                xmlSerializer.xmlWriter.write(
                    value.toString()
                        .replace("&", "&amp")
                        .replace("<", "&lt")
                        .replace(">", "&gt")
                        .replace("\"", "&quot")
                        .replace("'", "&apos")
                )
            }
            else {
                xmlSerializer.setState(ClassState(xmlSerializer))
                xmlSerializer.serialize(value)
            }
            xmlSerializer.xmlWriter.close(property.name)
        }
    }


}