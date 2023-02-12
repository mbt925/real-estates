package com.mbt925.realestate.feature.data

import java.time.OffsetDateTime

interface ClockProvider {

    fun now() : OffsetDateTime
}

class ClockProviderImpl : ClockProvider {

    override fun now() : OffsetDateTime = OffsetDateTime.now()
}
