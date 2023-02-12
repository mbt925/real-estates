package com.mbt925.realestate.feature.api

import com.mbt925.realestate.core.domain.ContextState

interface RealEstatesModel : ContextState<ListingsState> {

    suspend fun fetchListings()
}
