package com.presisco.gsonhelper

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.presisco.gsonhelper.typetoken.ListToken

open class ListHelper(
        prettyPrint: Boolean = false
) : SimpleHelper<List<*>>(
        ListToken.type,
        ListAdapter(),
        prettyPrint
) {
    companion object {

        class ListAdapter : UniversalAdapter<List<*>>() {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, src: List<*>) = writeList(writer, src)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): List<*> = readList(reader)

        }
    }
}