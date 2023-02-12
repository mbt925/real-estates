package com.mbt925.realestate.feature.api.models

sealed interface RealEstateResult {

    data class Success(
        val item: RealEstate,
        val timestamp: Long,
    ) : RealEstateResult

    sealed interface Failure : RealEstateResult {
        object MissingNetwork : Failure
        data class UnknownError(val message: String) : Failure
    }
}
