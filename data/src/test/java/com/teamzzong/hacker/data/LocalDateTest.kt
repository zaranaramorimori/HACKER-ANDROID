package com.teamzzong.hacker.data

import com.teamzzong.hacker.shared.datetime.LocalDateSerializer
import junit.framework.Assert.assertEquals
import kotlinx.datetime.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.junit.Test

class LocalDateTest {
    private fun dateStringSerialization(serializer: KSerializer<LocalDate>) {
        for ((localDate, json) in listOf(
            Pair(LocalDate(2022, 5, 31), "\"2022.05.31\""),
            Pair(LocalDate(2022, 5, 30), "\"2022.05.30\""),
        )) {
            assertEquals(localDate, Json.decodeFromString(serializer, json))
        }
    }

    @Test
    fun localDateSerializerTest() {
        dateStringSerialization(LocalDateSerializer)
    }
}
