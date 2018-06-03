package com.presisco.gsonhelper

import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class FuzzyHelper(
        prettyPrint: Boolean = false
) : SimpleHelper<Any>(
        FuzzToken().type,
        FuzzyAdapter(),
        prettyPrint
) {
    companion object {
        class FuzzToken : TypeToken<Any>()

        class FuzzyAdapter : UniversalAdapter<Any>() {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, fuzz: Any) = if (fuzz is List<*>) writeList(writer, fuzz) else writeMap(writer, fuzz as Map<*, *>)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): Any? = if (reader.peek() == JsonToken.BEGIN_ARRAY) readList(reader) else readMap(reader)
        }
    }
}