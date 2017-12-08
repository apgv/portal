package no.skotbuvel.portal.moshiadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZonedDateTime

class ZonedDateTimeAdapter {

    @ToJson
    fun toJson(zonedDateTime: ZonedDateTime) = zonedDateTime.toInstant().toEpochMilli()

    @FromJson
    fun fromJson(date: String) = ZonedDateTime.parse(date)
}