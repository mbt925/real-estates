package com.mbt925.realestate.service.local.models

import kotlinx.serialization.Serializable

@Serializable
data class ListingsResultDao(
    val items: List<RealEstateDao>? = null,
    val timestamp: Long = 0,
)
