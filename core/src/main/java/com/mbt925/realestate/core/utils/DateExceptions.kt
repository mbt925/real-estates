package com.mbt925.realestate.core.utils

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

fun Long.toOffsetDateTime(zoneId: ZoneId = ZoneId.systemDefault()): OffsetDateTime =
    OffsetDateTime.ofInstant(Instant.ofEpochSecond(this), zoneId)
