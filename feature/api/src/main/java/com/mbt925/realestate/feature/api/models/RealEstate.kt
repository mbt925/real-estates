package com.mbt925.realestate.feature.api.models

import com.mbt925.realestate.core.decimal.Money

data class RealEstate(
    val id: String,
    val bedrooms: Double?,
    val city: String,
    val area: Double,
    val imageUrl: String?,
    val price: Money,
    val agency: String,
    val propertyType: String,
    val rooms: Double?,
)
