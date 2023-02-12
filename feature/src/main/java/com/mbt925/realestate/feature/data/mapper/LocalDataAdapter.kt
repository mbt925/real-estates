package com.mbt925.realestate.feature.data.mapper

import com.mbt925.realestate.core.decimal.util.euroCents
import com.mbt925.realestate.core.utils.toOffsetDateTime
import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.api.models.RealEstate
import com.mbt925.realestate.service.local.models.ListingsResultDao
import com.mbt925.realestate.service.local.models.RealEstateDao

internal interface LocalDataAdapter {
    fun map(result: ListingsResult): ListingsResultDao?
    fun map(result: ListingsResultDao): ListingsResult?
}

internal class LocalDataAdapterImpl : LocalDataAdapter {

    override fun map(result: ListingsResult): ListingsResultDao? {
        return when (result) {
            is ListingsResult.Success -> ListingsResultDao(
                items = result.items.map { it.toRealEstateDao() },
                timestamp = result.dateTime.toEpochSecond()
            )
            else -> null
        }
    }

    override fun map(result: ListingsResultDao): ListingsResult? {
        return result.items?.let { items ->
            ListingsResult.Success(
                items = items.map { it.toRealEstate() },
                dateTime = result.timestamp.toOffsetDateTime()
            )
        }
    }

    private fun RealEstateDao.toRealEstate() = RealEstate(
        id = id,
        bedrooms = bedrooms,
        city = city,
        area = area,
        imageUrl = imageUrl,
        price = price.euroCents,
        agency = agency,
        propertyType = propertyType,
        rooms = rooms,
    )

    private fun RealEstate.toRealEstateDao() = RealEstateDao(
        id = id,
        bedrooms = bedrooms,
        city = city,
        area = area,
        imageUrl = imageUrl,
        price = price.minorUnit,
        agency = agency,
        propertyType = propertyType,
        rooms = rooms,
    )

}
