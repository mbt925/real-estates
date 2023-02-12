package com.mbt925.realestate.feature.ui.models

data class ListingsScreenParam(
    val isLoading: Boolean = true,
    val lastUpdate: String? = null,
    val listing: List<RealEstateParam>? = null,
    val failureParam: FailureParam? = null,
    val columns: Int = 1,
)
