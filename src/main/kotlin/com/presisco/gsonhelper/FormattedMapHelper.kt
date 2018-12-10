package com.presisco.gsonhelper

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.presisco.gsonhelper.typetoken.MapToken

class FormattedMapHelper(
        formatDef: Map<String, Set<String>>,
        prettyPrint: Boolean = false
) : SimpleHelper<Map<String, *>>(
        MapToken.type,
        MapAdapter(formatDef),
        prettyPrint
) {
    companion object {

        class MapAdapter(formatDef: Map<String, Set<String>>) : FormattedAdapter<Map<String, *>>(formatDef) {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: Map<String, *>) = writeMap(writer, src)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): Map<String, *> = readMap(reader)

        }
    }
}