package com.presisco.gsonhelper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import java.lang.reflect.Type

abstract class BaseHelper<T>(protected val type : Type, adapter : TypeAdapter<*>) {
    protected val gson :Gson = GsonBuilder()
            .registerTypeAdapter(type, adapter)
            .setPrettyPrinting()
            .create()

    abstract fun toJson(src : T) : String
    abstract fun fromJson(json:String) : T
}