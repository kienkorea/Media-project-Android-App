package com.example.up_down_android.util

import com.google.gson.*
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type
import java.util.*

class LocalDateTimeConverter : JsonDeserializer<LocalDateTime?>, JsonSerializer<LocalDateTime?> {

    private val format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").withLocale(Locale.KOREA)
    private val format2 =
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").withLocale(Locale.KOREA)

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        try {
            return format.parseLocalDateTime(json?.asString)
        } catch (e: Exception) {
        }
        try {
            return format2.parseLocalDateTime(json?.asString)
        } catch (e: Exception) {
        }
        return LocalDateTime.parse(json?.asString)
    }

    override fun serialize(
        src: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.toString("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA))
    }

}