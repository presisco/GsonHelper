package com.presisco.gsonhelper

import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

abstract class FormattedAdapter<T>(
        private val formatDef: Map<String, Set<String>>,
        private var createList: () -> MutableList<Any?> = { ArrayList() },
        private var createMap: () -> MutableMap<String, Any?> = { HashMap() }
) : TypeAdapter<T>() {
    private val formatMap: Map<String, String>

    init {
        formatMap = mutableMapOf()
        formatDef.forEach { type, keys ->
            if (type !in typeSet) {
                throw IllegalStateException("unsupported type: $type for keys: $keys")
            }
            keys.forEach { formatMap[it] = type }
        }
    }

    fun setCreateListFunc(func: () -> MutableList<Any?>): FormattedAdapter<T> {
        createList = func
        return this
    }

    fun setCreateMapFunc(func: () -> MutableMap<String, Any?>): FormattedAdapter<T> {
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
    protected fun readValue(reader: JsonReader, parentKey: String = ""): Pair<String?, Any?> {
        val token = reader.peek()
        val key = if (token == JsonToken.NAME) reader.nextName() else ""

        val value: Any? = when (reader.peek()) {
            JsonToken.NULL -> {
                reader.nextNull()
                null
            }
            JsonToken.BEGIN_ARRAY -> readList(reader, key)
            JsonToken.BEGIN_OBJECT -> readMap(reader, key)
            else -> {
                val typeKey = if (key.isEmpty()) {
                    parentKey
                } else {
                    key
                }
                if (!formatMap.containsKey(typeKey)) {
                    throw IllegalStateException("no definition for key: $typeKey")
                }
                when (formatMap[typeKey]) {
                    "string" -> reader.nextString()
                    "long" -> reader.nextLong()
                    "int" -> reader.nextInt()
                    "double" -> reader.nextDouble()
                    "boolean" -> reader.nextBoolean()
                    else -> throw IllegalStateException("unsupported type: ${formatMap[typeKey]}")
                }
            }
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
    fun readMap(reader: JsonReader, parentKey: String = ""): Map<String, Any?> {
        val map = createMap()
        reader.beginObject()
        while (reader.hasNext()) {
            val pair = readValue(reader, parentKey)
            map[pair.first!!] = pair.second
        }
        reader.endObject()
        return map
    }

    @Suppress("UNCHECKED_CAST")
    fun readList(reader: JsonReader, parentKey: String = ""): List<Any?> {
        val list = createList()
        reader.beginArray()
        while (reader.hasNext()) {
            list.add(readValue(reader, parentKey).second)
        }
        reader.endArray()
        return list
    }

    companion object {
        val typeSet = setOf("string", "long", "int", "double", "boolean")
    }
}