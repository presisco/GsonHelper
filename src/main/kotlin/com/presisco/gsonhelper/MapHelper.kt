package com.presisco.gsonhelper

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.presisco.gsonhelper.typetoken.MapToken

open class MapHelper(
        prettyPrint: Boolean = false
) : SimpleHelper<Map<String, *>>(
        MapToken.type,
        MapAdapter(),
        prettyPrint
) {
    companion object {

        class MapAdapter : UniversalAdapter<Map<String, *>>() {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: Map<String, *>) = writeMap(writer, src)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): Map<String, *> = readMap(reader)

        }
    }
}