package no.skotbuvel.portal.util

import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object JavaTimeUtil {

    fun nowEuropeOslo(): OffsetDateTime =
            ZonedDateTime
                    .now(ZoneId.of("Europe/Oslo"))
                    .toOffsetDateTime()
}