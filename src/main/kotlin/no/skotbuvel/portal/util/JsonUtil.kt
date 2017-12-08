package no.skotbuvel.portal.util

import com.squareup.moshi.Moshi
import no.skotbuvel.portal.moshiadapter.LocalDateAdapter
import no.skotbuvel.portal.moshiadapter.ZonedDateTimeAdapter

object JsonUtil {

    val moshi: Moshi = Moshi.Builder()
            .add(ZonedDateTimeAdapter())
            .add(LocalDateAdapter())
            .build()
}