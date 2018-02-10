package com.presisco.gsonhelper

import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class MapAdapter : TypeAdapter<HashMap<String, Any?>> {
    private var arrayAdapter: ArrayAdapter

    constructor() {
        arrayAdapter = ArrayAdapter(this@MapAdapter)
    }

    constructor(parent: ArrayAdapter) {
        arrayAdapter = parent
    }

    @Suppress("UNCHECKED_CAST")
    override fun write(writer: JsonWriter, src: HashMap<String, Any?>) {
        writer.beginObject()
        for ((key, value) in src) {
            writer.name(key)
            if (value == null) {
                writer.nullValue()
                continue
            }
            when (value) {
                is String -> writer.value(value)
                is Number -> writer.value(value)
                is Boolean -> writer.value(value)
                is Array<*> -> arrayAdapter.write(writer, value as Array<Any?>)
                is Map<*, *> -> write(writer, value as HashMap<String, Any?>)
                else -> throw JsonSyntaxException("unsupported type for key: $key, value: $value")
            }
        }
        writer.endObject()
    }

    @Suppress("UNCHECKED_CAST")
    override fun read(reader: JsonReader): HashMap<String, Any?> {
        val dst: HashMap<String, Any?> = hashMapOf()
        reader.beginObject()

        while (reader.hasNext()) {
            val key = reader.nextName()
            when (reader.peek()) {
                JsonToken.STRING -> dst[key] = reader.nextString()
                JsonToken.NUMBER -> dst[key] = reader.nextDouble()
                JsonToken.NULL -> {
                    dst[key] = null;reader.nextNull()
                }
                JsonToken.BOOLEAN -> dst[key] = reader.nextBoolean()
                JsonToken.BEGIN_OBJECT -> dst[key] = read(reader)
                JsonToken.BEGIN_ARRAY -> dst[key] = arrayAdapter.read(reader)
                else -> throw JsonSyntaxException("illegal token")
            }
        }
        reader.endObject()

        return dst
    }
}