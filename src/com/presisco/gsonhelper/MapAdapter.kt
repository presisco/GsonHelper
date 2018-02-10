package com.presisco.gsonhelper

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class MapAdapter : TypeAdapter<Map<Any,Any>> {
    private var arrayAdapter: ArrayAdapter

    constructor(){
        arrayAdapter= ArrayAdapter(this@MapAdapter)
    }

    constructor(parent : ArrayAdapter){
        arrayAdapter=parent
    }

    @Suppress("UNCHECKED_CAST")
    override fun write(writer: JsonWriter, src: Map<Any, Any>) {
        writer.beginObject()
        for((key,value) in src){
            when(key){
                is String -> writer.name(key)
                else -> writer.name(key.toString())
            }
            when(value){
                is String -> writer.value(value)
                is Int -> writer.value(value)
                is Float -> writer.value(value)
                is Double -> writer.value(value)
                is Boolean -> writer.value(value)
                is Array<*> -> arrayAdapter.write(writer,value as Array<Any>)
                is Map<*,*> -> write(writer,value as Map<Any, Any>)
            }
        }
        writer.endObject()
    }

    @Suppress("UNCHECKED_CAST")
    override fun read(reader: JsonReader): Map<Any, Any> {
        val dst : MutableMap<Any,Any> = mutableMapOf()
        reader.beginObject()
        while(reader.hasNext()){
            val key=reader.nextName()
            when (reader.peek()) {
                JsonToken.STRING -> dst[key] = reader.nextString()
                JsonToken.NUMBER -> dst[key] = reader.nextDouble()
                JsonToken.NULL -> dst[key] = null as Any
                JsonToken.BOOLEAN -> dst[key] = reader.nextBoolean()
                else -> {
                    try {
                        dst[key] = read(reader)
                    }catch (e : Exception){
                        dst[key] = arrayAdapter.read(reader)
                    }
                }
            }
        }
        reader.endObject()
        return dst
    }
}