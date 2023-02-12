package com.mbt925.realestate.feature.data

import com.mbt925.realestate.core.decimal.util.euroCents
import com.mbt925.realestate.core.utils.toOffsetDateTime
import com.mbt925.realestate.feature.api.models.RealEstate
import com.mbt925.realestate.service.local.models.RealEstateDao
import com.mbt925.realestate.service.remote.model.RealEstateDto
import java.time.OffsetDateTime


internal val realEstateDao = RealEstateDao(
    id = "1",
    bedrooms = 2.0,
    city = "city1",
    area = 1423.234,
    imageUrl = "imageUrl1",
    price = 123345,
    agency = "agency1",
    propertyType = "type1",
    rooms = 3.5,
)

internal val realEstateDto = RealEstateDto(
    id = 1,
    bedrooms = 2.0,
    city = "city1",
    area = 1423.234,
    imageUrl = "imageUrl1",
    price = 1233.45,
    agency = "agency1",
    propertyType = "type1",
    rooms = 3.5,
)

internal val realEstate = RealEstate(
    id = "1",
    bedrooms = 2.0,
    city = "city1",
    area = 1423.234,
    imageUrl = "imageUrl1",
    price = 123345L.euroCents,
    agency = "agency1",
    propertyType = "type1",
    rooms = 3.5,
)

internal val offsetDateTime = run {
    val offset = OffsetDateTime.now().offset
    val date = OffsetDateTime.of(2000, 1, 1, 5, 3, 2, 6, offset)
    date.toEpochSecond().toOffsetDateTime()
}
