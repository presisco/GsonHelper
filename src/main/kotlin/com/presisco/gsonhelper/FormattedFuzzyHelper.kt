package com.presisco.gsonhelper

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.presisco.gsonhelper.typetoken.FuzzyToken

class FormattedFuzzyHelper(
        formatDef: Map<String, Set<String>>,
        prettyPrint: Boolean = false
) : SimpleHelper<Any>(
        FuzzyToken.type,
        FuzzyAdapter(formatDef),
        prettyPrint
) {
    companion object {

        class FuzzyAdapter(formatDef: Map<String, Set<String>>) : FormattedAdapter<Any>(formatDef) {

            @Suppress("UNCHECKED_CAST")
            override fun write(writer: JsonWriter, fuzz: Any) = if (fuzz is List<*>) writeList(writer, fuzz) else writeMap(writer, fuzz as Map<*, *>)

            @Suppress("UNCHECKED_CAST")
            override fun read(reader: JsonReader): Any = if (reader.peek() == JsonToken.BEGIN_ARRAY) readList(reader) else readMap(reader)
        }
    }
}