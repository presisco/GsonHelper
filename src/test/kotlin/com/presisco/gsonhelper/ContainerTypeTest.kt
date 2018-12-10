package com.presisco.gsonhelper

import org.junit.Test
import kotlin.test.expect

class ContainerTypeTest {

    @Test
    fun typeTest() {
        val helper = FuzzyHelper()
        val formattedHelper = FormattedFuzzyHelper(formatDef = mapOf(
                "string" to setOf("a"),
                "int" to setOf("b")
        ))
        val list = arrayListOf(
                hashMapOf("a" to "a", "b" to 1),
                hashMapOf("a" to "b", "b" to 2)
        )
        var json = helper.toJson(list)
        var converted = helper.fromJson(json)
        expect(true) { converted is ArrayList<*> }
        expect(true) { (converted as List<*>)[0] is HashMap<*, *> }
        json = formattedHelper.toJson(list)
        converted = formattedHelper.fromJson(json)
        expect(true) { converted is ArrayList<*> }
        expect(true) { (converted as List<*>)[0] is HashMap<*, *> }
    }
}