package com.presisco.gsonhelper

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

open class ArrayHelper(
        prettyPrint: Boolean = false
) : SimpleHelper<Array<Any?>>(
        ArrayToken().type,
        ArrayAdapter(),
        prettyPrint
) {
    companion object {
        class ArrayToken : TypeToken<Map<String, Any?>>()

        class ArrayAdapter : UniversalAdapter<Array<*>>() {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: Array<*>) = writeList(writer, src.toList())

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader) = readList(reader).toTypedArray()

        }
    }
}