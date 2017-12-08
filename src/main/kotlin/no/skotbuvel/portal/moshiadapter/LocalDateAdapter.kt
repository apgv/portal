package no.skotbuvel.portal.moshiadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate

class LocalDateAdapter {

    @ToJson
    fun toJson(localDate: LocalDate) = localDate.toString()

    @FromJson
    fun fromJson(date: String) = LocalDate.parse(date)
}