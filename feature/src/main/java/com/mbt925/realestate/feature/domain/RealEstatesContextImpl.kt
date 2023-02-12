package com.mbt925.realestate.feature.domain

import com.mbt925.realestate.core.domain.Context
import com.mbt925.realestate.feature.api.ListingsState
import com.mbt925.realestate.feature.api.RealEstatesModel
import com.mbt925.realestate.feature.api.RealEstatesRepository

internal class RealEstatesContextImpl(
    private val realEstatesRepository: RealEstatesRepository,
) : Context<ListingsState> by Context(
    initialState = ListingsState(),
), RealEstatesModel {

    override suspend fun fetchListings() {
        val useCase = FetchListingsUseCase(
            realEstatesRepository = realEstatesRepository,
        )
        execute(useCase, fetchListingsReducer)
    }

}
