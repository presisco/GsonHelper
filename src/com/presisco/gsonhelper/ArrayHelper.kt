package com.presisco.gsonhelper

class ArrayHelper : BaseHelper<Array<Any?>>(ArrayToken().type, ArrayAdapter()) {

    override fun toJson(src: Array<Any?>): String {
        return gson.toJson(src, type)
    }

    override fun fromJson(json: String): Array<Any?> {
        return gson.fromJson(json, type)
    }

}