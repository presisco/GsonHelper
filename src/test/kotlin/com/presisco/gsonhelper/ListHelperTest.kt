package com.presisco.gsonhelper

import org.junit.Test
import kotlin.test.expect

class ListHelperTest {
    private val listHelper = ListHelper()

    @Test
    fun validate() {
        val stringList = listOf("james", "bob", null, "jack")

        val json = listHelper.toJson(stringList)

        expect(
                "[\"james\",\"bob\",null,\"jack\"]",
                { listHelper.toJson(stringList) }
        )

        val reversed = listHelper.fromJson(json)

        reversed.mapIndexed { index, item -> expect(stringList[index], { item }) }

    }
}