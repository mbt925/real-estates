package com.mbt925.realestate.feature.ui.details

import com.mbt925.realestate.feature.ui.models.RealEstateParam

data class RealEstateDetailsScreenParam(
    val item: RealEstateParam,
    val hasVerticalLayout: Boolean = true,
)
