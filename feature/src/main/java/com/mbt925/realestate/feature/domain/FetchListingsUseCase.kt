package com.mbt925.realestate.feature.domain

import com.mbt925.realestate.core.domain.UseCase
import com.mbt925.realestate.core.domain.reducer
import com.mbt925.realestate.feature.api.ListingsState
import com.mbt925.realestate.feature.api.RealEstatesRepository
import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.domain.FetchListingsEffect.Failure
import com.mbt925.realestate.feature.domain.FetchListingsEffect.Loading
import com.mbt925.realestate.feature.domain.FetchListingsEffect.Success
import kotlinx.coroutines.flow.flow

internal class FetchListingsUseCase(
    private val realEstatesRepository: RealEstatesRepository,
) : UseCase<FetchListingsEffect> {

    override suspend fun execute() = flow {
        emit(Loading)
        val dataAccess = realEstatesRepository.getListings().getAndFetch()

        dataAccess.collect { result ->
            when (result) {
                is ListingsResult.Success -> emit(Success(result))
                is ListingsResult.Failure -> emit(Failure(result))
            }
        }
    }
}

internal sealed interface FetchListingsEffect {
    object Loading : FetchListingsEffect
    data class Success(val value: ListingsResult.Success) : FetchListingsEffect

    data class Failure(val error: ListingsResult.Failure) : FetchListingsEffect
}

internal val fetchListingsReducer = reducer<ListingsState, FetchListingsEffect> { effect ->
    when (effect) {
        Loading -> copy(loading = true)
        is Failure -> copy(
            loading = false,
            failure = effect.error,
        )
        is Success -> copy(
            loading = false,
            listings = effect.value,
            failure = null,
        )
    }
}
