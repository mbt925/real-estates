package com.mbt925.realestate.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsDto(
    @SerialName("items")
    val items: List<RealEstateDto>,
)
