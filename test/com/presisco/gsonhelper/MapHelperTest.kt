package com.presisco.gsonhelper

import org.junit.Test
import kotlin.test.expect

class MapHelperTest {

    @Test
    fun typeValidate() {
        val mapHelper = MapHelper()

        val testMap = hashMapOf(
                "boolean" to true,
                "string" to "text",
                "null" to null,
                "array" to arrayOf("james", "bob", null, "karry"),
                "long" to Long.MAX_VALUE,
                "short" to Short.MAX_VALUE,
                "int" to Int.MAX_VALUE,
                "float" to Float.MAX_VALUE,
                "double" to Double.MAX_VALUE
        )

        expect(
                "{\"boolean\":true," +
                        "\"string\":\"text\"," +
                        "\"null\":null," +
                        "\"array\":[\"james\",\"bob\",null,\"karry\"]," +
                        "\"double\":1.7976931348623157E308," +
                        "\"short\":32767," +
                        "\"float\":3.4028235E38," +
                        "\"long\":9223372036854775807," +
                        "\"int\":2147483647}"
                , { mapHelper.toJson(testMap as HashMap<String, Any?>) })
    }

}