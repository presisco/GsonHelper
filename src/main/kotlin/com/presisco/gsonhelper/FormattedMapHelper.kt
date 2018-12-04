package com.presisco.gsonhelper

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class FormattedMapHelper(
        formatDef: Map<String, Set<String>>,
        prettyPrint: Boolean = false
) : SimpleHelper<Map<String, *>>(
        MapToken().type,
        MapAdapter(formatDef),
        prettyPrint
) {
    companion object {
        class MapToken : TypeToken<Map<String, *>>()

        class MapAdapter(formatDef: Map<String, Set<String>>) : FormattedAdapter<Map<String, *>>(formatDef) {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: Map<String, *>) = writeMap(writer, src)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): Map<String, *> = readMap(reader)

        }
    }
}