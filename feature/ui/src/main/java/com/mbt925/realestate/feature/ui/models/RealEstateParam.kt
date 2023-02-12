package com.mbt925.realestate.feature.ui.models

import com.mbt925.realestate.design.phrase.PhraseHolder

data class RealEstateParam(
    val id: String,
    val bedrooms: String?,
    val city: String,
    val area: PhraseHolder,
    val imageUrl: String?,
    val price: String,
    val agency: String,
    val propertyType: String,
    val rooms: String?,
)
