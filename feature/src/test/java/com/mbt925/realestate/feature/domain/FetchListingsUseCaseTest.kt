package com.mbt925.realestate.feature.domain

import com.mbt925.realestate.core.data.DataAccess
import com.mbt925.realestate.core.data.invoke
import com.mbt925.realestate.core.test.TaskExecutorRule
import com.mbt925.realestate.feature.api.RealEstatesRepository
import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.api.models.ListingsResult.Failure
import com.mbt925.realestate.feature.api.models.ListingsResult.Success
import com.mbt925.realestate.feature.data.offsetDateTime
import com.mbt925.realestate.feature.data.realEstate
import com.mbt925.realestate.feature.domain.FetchListingsEffect.Loading
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toCollection
import org.junit.Rule
import org.junit.Test

class FetchListingsUseCaseTest {

    @get:Rule
    val rule = TaskExecutorRule()

    private val repository = mockk<RealEstatesRepository> {
        coEvery { getListings() } returns DataAccess { Failure.MissingNetwork }
    }

    @Test
    fun onExecute_callsRepository() = rule.runTest {
        getUseCase().execute().collect()
        coVerify { repository.getListings() }
    }

    @Test
    fun execute_onSuccess_emitsLoading_thenSuccess() = rule.runTest {
        verifyEffects(
            expectedResult = Success(items = listOf(realEstate), offsetDateTime),
        )
    }

    @Test
    fun execute_onMissingNetworkFailure_emitsLoading_thenFailure() = rule.runTest {
        verifyEffects(
            expectedResult = Failure.MissingNetwork,
        )
    }

    @Test
    fun execute_onUnknownErrorFailure_emitsLoading_thenFailure() = rule.runTest {
        verifyEffects(
            expectedResult = Failure.UnknownError("message"),
        )
    }

    private suspend fun verifyEffects(
        expectedResult: ListingsResult,
    ) {
        coEvery { repository.getListings() } returns DataAccess { expectedResult }

        val effects = mutableListOf<FetchListingsEffect>()

        getUseCase().execute().toCollection(effects)

        assertEquals(Loading, effects[0])
        when (expectedResult) {
            is Success -> assertEquals(FetchListingsEffect.Success(expectedResult), effects[1])
            is Failure -> assertEquals(FetchListingsEffect.Failure(expectedResult), effects[1])
        }
    }

    private fun getUseCase() = FetchListingsUseCase(realEstatesRepository = repository)

}
