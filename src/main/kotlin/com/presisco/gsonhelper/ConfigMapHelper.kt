package com.presisco.gsonhelper

import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.nio.charset.Charset

class ConfigMapHelper : MapHelper(prettyPrint = true) {

    fun readConfigMap(path: String): Map<String, *> {
        val configFile = File(path)
        val buffer = ByteArray(configFile.length().toInt())
        val fis = FileInputStream(configFile)
        fis.read(buffer)
        fis.close()
        val fileStr = String(buffer, Charset.forName("UTF-8"))
        return fromJson(fileStr)
    }

    fun writeConfigMap(path: String, configMap: Map<String, *>) {
        val writer = FileWriter(path)
        writer.write(toJson(configMap))
        writer.close()
    }

}