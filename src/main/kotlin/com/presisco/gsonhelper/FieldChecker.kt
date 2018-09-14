package com.presisco.gsonhelper

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonToken

class FieldChecker(fields: Map<String, JsonToken>) {

    private val gson = GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, FieldCheckAdapter(fields))
            .setPrettyPrinting()
            .create()

    fun check(json: String): Boolean {
        return try {
            gson.fromJson(json, Boolean::class.java)
        } catch (e: Exception) {
            false
        }
    }
}