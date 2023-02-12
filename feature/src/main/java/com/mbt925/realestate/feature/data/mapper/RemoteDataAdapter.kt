package com.mbt925.realestate.feature.data.mapper

import com.mbt925.realestate.core.decimal.util.euroCents
import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.api.models.RealEstate
import com.mbt925.realestate.feature.api.models.RealEstateResult
import com.mbt925.realestate.feature.data.ClockProvider
import com.mbt925.realestate.networking.models.ApiResponse
import com.mbt925.realestate.service.remote.model.ListingsDto
import com.mbt925.realestate.service.remote.model.RealEstateDto
import java.io.IOException

internal interface RemoteDataAdapter {

    fun mapListings(response: ApiResponse<ListingsDto>): ListingsResult
    fun mapItem(response: ApiResponse<RealEstateDto>): RealEstateResult
}

internal class RemoteDataAdapterImpl(
    private val clockProvider: ClockProvider,
) : RemoteDataAdapter {

    override fun mapListings(response: ApiResponse<ListingsDto>): ListingsResult =
        when (response) {
            is ApiResponse.Success -> ListingsResult.Success(
                items = requireNotNull(response.data?.items).map { it.toRealEstate() },
                dateTime = clockProvider.now(),
            )
            is ApiResponse.Failure.Known -> ListingsResult.Failure.UnknownError(
                message = response.error.message ?: "Unknown error"
            )
            is ApiResponse.Failure.Unknown -> when (val error = response.error) {
                is IOException -> ListingsResult.Failure.MissingNetwork
                else -> ListingsResult.Failure.UnknownError(
                    message = error.message ?: error::class.toString()
                )
            }
        }

    override fun mapItem(response: ApiResponse<RealEstateDto>): RealEstateResult =
        when (response) {
            is ApiResponse.Success -> RealEstateResult.Success(
                item = requireNotNull(response.data).toRealEstate(),
                timestamp = clockProvider.now().toEpochSecond(),
            )
            is ApiResponse.Failure.Known -> RealEstateResult.Failure.UnknownError(
                message = response.error.message ?: "Unknown error"
            )
            is ApiResponse.Failure.Unknown -> when (val error = response.error) {
                is IOException -> RealEstateResult.Failure.MissingNetwork
                else -> RealEstateResult.Failure.UnknownError(
                    message = error.message ?: error::class.toString()
                )
            }
        }

    private fun RealEstateDto.toRealEstate() = RealEstate(
        id = id.toString(),
        bedrooms = bedrooms,
        city = city,
        area = area,
        imageUrl = imageUrl,
        price = price.euroCents,
        agency = agency,
        propertyType = propertyType,
        rooms = rooms,
    )

}
