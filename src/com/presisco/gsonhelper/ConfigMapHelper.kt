package com.presisco.gsonhelper

import com.google.gson.GsonBuilder
import java.io.*
import java.nio.charset.Charset

class ConfigMapHelper {
    private var gson=GsonBuilder()
            .registerTypeAdapter(MapToken().type, MapAdapter())
            .setPrettyPrinting()
            .create()

    fun readConfigMap(path : String) : Map<String,Any>{
        val configFile = File(path)
        val buffer = ByteArray(configFile.length().toInt())
        val fis=FileInputStream(configFile)
        fis.read(buffer)
        fis.close()
        val fileStr = String(buffer,Charset.forName("UTF-8"))
        return gson.fromJson(fileStr, MapToken().type)
    }

    fun writeConfigMap(path : String, configMap : Map<String,Any?>){
        val writer = FileWriter(path)
        writer.write(gson.toJson(configMap, MapToken().type))
        writer.close()
    }
}