package com.presisco.gsonhelper

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

open class MapHelper(
        prettyPrint: Boolean = false
) : SimpleHelper<Map<String, Any?>>(
        MapToken().type,
        MapAdapter(),
        prettyPrint
) {
    companion object {
        class MapToken : TypeToken<Map<String, *>>()

        class MapAdapter : UniversalAdapter<Map<String, *>>() {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: Map<String, *>) = writeMap(writer, src)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): Map<String, *> = readMap(reader)

        }
    }
}