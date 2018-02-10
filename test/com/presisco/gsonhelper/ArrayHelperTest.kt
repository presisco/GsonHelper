package com.presisco.gsonhelper

import org.junit.Test
import kotlin.test.expect

class ArrayHelperTest {
    private val arrayHelper = ArrayHelper()

    @Test
    fun validate() {
        val stringArray = arrayOf<Any?>("james", "bob", null, "jack")

        val json = arrayHelper.toJson(stringArray)

        expect(
                "[\"james\",\"bob\",null,\"jack\"]",
                { arrayHelper.toJson(stringArray) }
        )

        val reversed = arrayHelper.fromJson(json)

        reversed.mapIndexed { index, item -> expect(stringArray[index], { item }) }

    }
}