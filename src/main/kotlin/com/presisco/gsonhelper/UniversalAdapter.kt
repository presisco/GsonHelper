package com.presisco.gsonhelper

import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

abstract class UniversalAdapter<T>(
        private var createList: () -> MutableList<Any?> = { ArrayList() },
        private var createMap: () -> MutableMap<String, Any?> = { HashMap() }
) : TypeAdapter<T>() {

    fun setCreateListFunc(func: () -> MutableList<Any?>): UniversalAdapter<T> {
        createList = func
        return this
    }

    fun setCreateMapFunc(func: () -> MutableMap<String, Any?>): UniversalAdapter<T> {
        createMap = func
        return this
    }

    protected fun writeValue(writer: JsonWriter, key: String?, value: Any?) {
        if (key != null) {
            writer.name(key)
        }
        if (value != null) {
            when (value) {
                is String -> writer.value(value)
                is Number -> writer.value(value)
                is Boolean -> writer.value(value)
                is List<*> -> writeList(writer, value)
                is Map<*, *> -> writeMap(writer, value)
                else -> throw JsonSyntaxException("unsupported type for key: $key, value: $value")
            }
        } else {
            writer.nullValue()
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun readValue(reader: JsonReader): Pair<String?, Any?> {
        val token = reader.peek()
        val key = if (token == JsonToken.NAME) reader.nextName() else null

        val value: Any? = when (reader.peek()) {
            JsonToken.STRING -> reader.nextString()
            JsonToken.NUMBER -> reader.nextDouble()
            JsonToken.NULL -> {
                reader.nextNull()
                null
            }
            JsonToken.BOOLEAN -> reader.nextBoolean()
            JsonToken.BEGIN_OBJECT -> readMap(reader)
            JsonToken.BEGIN_ARRAY -> readList(reader)
            else -> throw JsonSyntaxException("illegal token: $token")
        }
        return Pair(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    fun writeMap(writer: JsonWriter, src: Map<*, *>) {
        writer.beginObject()
        (src as Map<String, Any?>).forEach { key, value ->
            writeValue(writer, key, value)
        }
        writer.endObject()
    }

    @Suppress("UNCHECKED_CAST")
    fun writeList(writer: JsonWriter, src: List<*>) {
        writer.beginObject()
        src.forEach { value ->
            writeValue(writer, null, value)
        }
        writer.endObject()
    }

    @Suppress("UNCHECKED_CAST")
    fun readMap(reader: JsonReader): Map<String, Any?> {
        val map = createMap()
        reader.beginObject()
        while (reader.hasNext()) {
            val pair = readValue(reader)
            map[pair.first!!] = pair.second
        }
        reader.endObject()
        return map
    }

    @Suppress("UNCHECKED_CAST")
    fun readList(reader: JsonReader): List<Any?> {
        val list = createList()
        reader.beginArray()
        while (reader.hasNext()) {
            list.add(readValue(reader).second)
        }
        reader.endArray()
        return list
    }

}