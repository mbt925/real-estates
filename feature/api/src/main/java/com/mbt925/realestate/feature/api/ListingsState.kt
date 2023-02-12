package com.mbt925.realestate.feature.api

import com.mbt925.realestate.feature.api.models.ListingsResult

data class ListingsState(
    val loading: Boolean = false,
    val listings: ListingsResult.Success? = null,
    val failure: ListingsResult.Failure? = null,
)
