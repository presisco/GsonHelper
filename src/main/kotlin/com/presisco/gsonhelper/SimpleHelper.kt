package com.presisco.gsonhelper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import java.lang.reflect.Type

abstract class SimpleHelper<T>(
        protected val type: Type,
        adapter: TypeAdapter<*>,
        prettyPrint: Boolean
) {
    protected val gson: Gson

    init {
        val builder = GsonBuilder()
                .registerTypeAdapter(type, adapter)
                .serializeNulls()

        if (prettyPrint)
            builder.setPrettyPrinting()

        gson = builder.create()
    }

    open fun toJson(src: T) = gson.toJson(src)
    open fun fromJson(json: String): T = gson.fromJson(json, type)

}