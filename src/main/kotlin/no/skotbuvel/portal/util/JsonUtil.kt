package no.skotbuvel.portal.util

import com.squareup.moshi.Moshi
import no.skotbuvel.portal.person.ZonedDateTimeAdapter

object JsonUtil {

    val moshi: Moshi = Moshi.Builder()
            .add(ZonedDateTimeAdapter())
            .build()
}