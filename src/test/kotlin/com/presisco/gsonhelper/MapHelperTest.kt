package com.presisco.gsonhelper

import org.junit.Test
import kotlin.test.expect

class MapHelperTest {
    private val sampleJson = "{\n" +
            "  \"boolean\": true,\n" +
            "  \"string\": \"text\",\n" +
            "  \"null\": null,\n" +
            "  \"array\": [\n" +
            "    \"james\",\n" +
            "    \"bob\",\n" +
            "    null,\n" +
            "    \"karry\"\n" +
            "  ],\n" +
            "  \"double\": 1.7976931348623157E308,\n" +
            "  \"short\": 32767,\n" +
            "  \"float\": 3.4028235E38,\n" +
            "  \"map\": {\n" +
            "    \"a\": 1.0,\n" +
            "    \"b\": [\n" +
            "      1,\n" +
            "      2,\n" +
            "      3\n" +
            "    ],\n" +
            "    \"c\": {\n" +
            "      \"d\": 1,\n" +
            "      \"e\": 2,\n" +
            "      \"f\": \"3\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"long\": 9.223372036854776E18,\n" +
            "  \"int\": 2.147483647E9\n" +
            "}"

    private val sampleConvertedJson = "{\n" +
            "  \"boolean\": true,\n" +
            "  \"string\": \"text\",\n" +
            "  \"null\": null,\n" +
            "  \"array\": [\n" +
            "    \"james\",\n" +
            "    \"bob\",\n" +
            "    null,\n" +
            "    \"karry\"\n" +
            "  ],\n" +
            "  \"double\": 1.7976931348623157E308,\n" +
            "  \"short\": 32767.0,\n" +
            "  \"float\": 3.4028235E38,\n" +
            "  \"map\": {\n" +
            "    \"a\": 1.0,\n" +
            "    \"b\": [\n" +
            "      1.0,\n" +
            "      2.0,\n" +
            "      3.0\n" +
            "    ],\n" +
            "    \"c\": {\n" +
            "      \"d\": 1.0,\n" +
            "      \"e\": 2.0,\n" +
            "      \"f\": \"3\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"long\": 9.223372036854776E18,\n" +
            "  \"int\": 2.147483647E9\n" +
            "}"

    @Test
    fun typeValidate() {
        val mapHelper = MapHelper(prettyPrint = true)

        val map = mapHelper.fromJson(sampleJson)

        expect(sampleConvertedJson, { mapHelper.toJson(map) })
    }

}