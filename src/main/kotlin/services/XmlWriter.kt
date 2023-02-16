package services

class XmlWriter {

    private val openTags: MutableList<String> = mutableListOf()
    private val stringBuilder = StringBuilder()

    fun open(tag: String) {
        openTags.add(tag)
        stringBuilder.append("<")
            .append(tag.sanitize())
            .append(">")
    }

    fun close(tag: String) {
        val lastIndexOfTag = openTags.lastIndexOf(tag)
        if(lastIndexOfTag == openTags.size)
            throw IllegalArgumentException("Tag can't be closed if it isn't open")
        openTags.removeAt(lastIndexOfTag)

        stringBuilder.append("</")
            .append(tag.sanitize())
            .append(">")
    }

    fun write(value: String?) {
        if (value == null)
            return
        stringBuilder.append(value.sanitize())
    }

    fun compile(): String {
        return stringBuilder.toString()
    }

    private fun String.sanitize(): String {
        return this
            .replace("&", "&amp")
            .replace("<", "&lt")
            .replace(">", "&gt")
            .replace("\"", "&quot")
            .replace("'", "&apos")
    }
}