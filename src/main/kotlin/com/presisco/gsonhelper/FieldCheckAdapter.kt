package com.presisco.gsonhelper

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class FieldCheckAdapter(
        private val fields: Map<String, JsonToken>
) : TypeAdapter<Boolean>() {

    override fun write(writer: JsonWriter, result: Boolean) {
        writer.beginObject()
        for (field in fields.keys) {
            writer.name(field).value("need to check")
        }
        writer.endObject()
    }

    private fun nextObject(reader: JsonReader): Any = when (reader.peek()) {
        JsonToken.NUMBER -> reader.nextDouble()
        JsonToken.STRING -> reader.nextString()
        JsonToken.BOOLEAN -> reader.nextBoolean()
        else -> "bad value"
    }

    private fun readObject(reader: JsonReader): Boolean {
        var hasUndefined = false
        var unmatchedType = false
        val sb = StringBuffer("")

        reader.beginObject()
        val checklist = fields.keys.toMutableSet()

        while (reader.hasNext()) {
            val key = reader.nextName()
            val valueType = reader.peek()

            if (checklist.contains(key)) {
                if (fields[key] == valueType || valueType == JsonToken.NULL) {
                    checklist.remove(key)
                } else {
                    sb.append("unmatched type for key: $key, received type: $valueType, value: ${nextObject(reader)}\n")
                    unmatchedType = true
                }
            } else {
                sb.append("undefined key: $key, received type: $valueType, value: ${nextObject(reader)}\n")
                hasUndefined = true
            }

            reader.skipValue()
        }

        if (checklist.isNotEmpty()) {
            sb.append("lacking fields:")
            for (field in checklist)
                sb.append(" ").append(field)
        }

        reader.endObject()

        if (hasUndefined)
            throw NoSuchFieldException(sb.toString())
        else if (unmatchedType)
            throw IllegalArgumentException(sb.toString())
        else if (checklist.isNotEmpty())
            throw IllegalArgumentException(sb.toString())
        else
            return true
    }

    private fun readArray(reader: JsonReader): Boolean {
        reader.beginArray()
        while (reader.hasNext())
            readObject(reader)
        reader.endArray()
        return true
    }

    override fun read(reader: JsonReader): Boolean {
        return try {
            return when (reader.peek()) {
                JsonToken.BEGIN_OBJECT -> readObject(reader)
                JsonToken.BEGIN_ARRAY -> readArray(reader)
                else -> {
                    println("illegal begin: ${reader.peek()}")
                    false
                }
            }
        } catch (e: Exception) {
            println("parse exception: " + e.message)
            false
        }
    }

    companion object {
        const val PASS = "pass"
    }
}


