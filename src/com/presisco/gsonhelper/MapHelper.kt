package com.presisco.gsonhelper

class MapHelper : BaseHelper<HashMap<String, Any>>(MapToken().type, MapAdapter()){

    override fun toJson(src : HashMap<String,Any>) : String {
        return gson.toJson(src, type)
    }

    override fun fromJson(json : String) : HashMap<String,Any>{
        return gson.fromJson(json,type)
    }

}