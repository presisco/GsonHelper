package com.presisco.gsonhelper

import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class ArrayAdapter : TypeAdapter<Array<Any>>{
    private var mapAdapter : MapAdapter

    constructor(){
        mapAdapter = MapAdapter(this@ArrayAdapter)
    }

    constructor(parent : MapAdapter){
        mapAdapter = parent
    }

    @Suppress("UNCHECKED_CAST")
    override fun write(writer: JsonWriter, src: Array<Any>){
        writer.beginArray()
        for(item in src){
            when(item){
                is Map<*,*> -> mapAdapter.write(writer,item as Map<Any,Any>)
                is Array<*> -> write(writer,item as Array<Any>)
                is String -> writer.value(item)
                is Int -> writer.value(item)
                is Float -> writer.value(item)
                is Double -> writer.value(item)
                is Boolean -> writer.value(item)
            }
        }
        writer.endArray()
    }

    @Suppress("UNCHECKED_CAST")
    override fun read(reader: JsonReader) : Array<Any>{
        val dst : ArrayList<Any> = arrayListOf()
        reader.beginArray()
        while(reader.hasNext()){
            when(reader.peek()){
                JsonToken.STRING -> dst.add(reader.nextString())
                JsonToken.BOOLEAN -> dst.add(reader.nextBoolean())
                JsonToken.NUMBER -> dst.add(reader.nextDouble())
                JsonToken.NULL -> dst.add(reader.nextNull())
                JsonToken.BEGIN_OBJECT -> dst.add(mapAdapter.read(reader))
                JsonToken.BEGIN_ARRAY -> dst.add(read(reader))
                else -> throw JsonSyntaxException("illegal token")
            }
        }
        reader.endArray()
        return dst.toArray()
    }
}