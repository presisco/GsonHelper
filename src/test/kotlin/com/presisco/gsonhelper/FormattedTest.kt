package com.presisco.gsonhelper

import org.junit.Test
import kotlin.test.expect

class FormattedTest {

    @Test
    fun mapTest() {
        val helper = FormattedMapHelper(
                mapOf(
                        "string" to setOf("name", "gender", "friends"),
                        "byte" to setOf("age"),
                        "float" to setOf("weight"),
                        "long" to setOf("id"),
                        "boolean" to setOf("good")
                )
        )

        val original = mapOf(
                "name" to "james",
                "age" to 18.toByte(),
                "gender" to "male",
                "weight" to 75.5.toFloat(),
                "id" to 12345678909876L,
                "good" to true,
                "friends" to listOf(
                        "bob",
                        "tom"
                )
        )

        val json = helper.toJson(original)
        println(json)

        expect(original) { helper.fromJson(json) }
    }
}