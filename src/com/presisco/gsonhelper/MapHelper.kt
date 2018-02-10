package com.presisco.gsonhelper

class MapHelper : BaseHelper<Map<String, Any>>(MapToken().type, MapAdapter()){

    override fun toJson(src : Map<String,Any>) : String {
        return gson.toJson(src, type)
    }

    override fun fromJson(json : String) : Map<String,Any>{
        return gson.fromJson(json,type)
    }

}