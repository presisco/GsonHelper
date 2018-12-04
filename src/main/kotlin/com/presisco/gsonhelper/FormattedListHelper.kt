package com.presisco.gsonhelper

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class FormattedListHelper(
        formatDef: Map<String, Set<String>>,
        prettyPrint: Boolean = false
) : SimpleHelper<List<*>>(
        ListToken().type,
        ListAdapter(formatDef),
        prettyPrint
) {
    companion object {
        class ListToken : TypeToken<List<*>>()

        class ListAdapter(formatDef: Map<String, Set<String>>) : FormattedAdapter<List<*>>(formatDef) {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: List<*>) = writeList(writer, src)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): List<*> = readList(reader)

        }
    }
}