package com.presisco.gsonhelper

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

open class ListHelper(
        prettyPrint: Boolean = false
) : SimpleHelper<List<*>>(
        ListToken().type,
        ListAdapter(),
        prettyPrint
) {
    companion object {
        class ListToken : TypeToken<List<*>>()

        class ListAdapter : UniversalAdapter<List<*>>() {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: List<*>) = writeList(writer, src)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): List<*> = readList(reader)

        }
    }
}