package com.mbt925.realestate.service.local.models

import kotlinx.serialization.Serializable

@Serializable
data class RealEstateDao(
    val id: String,
    val bedrooms: Double?,
    val city: String,
    val area: Double,
    val imageUrl: String?,
    val price: Long,
    val agency: String,
    val propertyType: String,
    val rooms: Double?,
)
