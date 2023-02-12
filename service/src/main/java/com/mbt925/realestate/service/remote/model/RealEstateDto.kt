package com.mbt925.realestate.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RealEstateDto(
    @SerialName("id")
    val id: Int,
    @SerialName("bedrooms")
    val bedrooms: Double? = null,
    @SerialName("city")
    val city: String,
    @SerialName("area")
    val area: Double,
    @SerialName("url")
    val imageUrl: String? = null,
    @SerialName("price")
    val price: Double,
    @SerialName("professional")
    val agency: String,
    @SerialName("propertyType")
    val propertyType: String,
    @SerialName("rooms")
    val rooms: Double? = null,
)
