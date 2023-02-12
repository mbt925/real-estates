package com.mbt925.realestate.feature.api.models

import java.time.OffsetDateTime

sealed interface ListingsResult {

    data class Success(
        val items: List<RealEstate>,
        val dateTime: OffsetDateTime,
    ) : ListingsResult

    sealed interface Failure : ListingsResult {
        object MissingNetwork : Failure
        data class UnknownError(val message: String) : Failure
    }
}
