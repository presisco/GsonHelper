package com.presisco.gsonhelper

class ArrayOrMapHelper : BaseHelper<Array<Any?>>(ArrayToken().type, ArrayOrMapAdapter()) {

    override fun toJson(src: Array<Any?>): String {
        return gson.toJson(src, type)
    }

    override fun fromJson(json: String): Array<Any?> {
        return gson.fromJson(json, type)
    }

}