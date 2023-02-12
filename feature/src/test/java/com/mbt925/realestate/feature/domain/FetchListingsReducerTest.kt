package com.mbt925.realestate.feature.domain

import com.mbt925.realestate.core.test.TaskExecutorRule
import com.mbt925.realestate.feature.api.ListingsState
import com.mbt925.realestate.feature.api.models.ListingsResult.Failure
import com.mbt925.realestate.feature.api.models.ListingsResult.Success
import com.mbt925.realestate.feature.data.offsetDateTime
import com.mbt925.realestate.feature.data.realEstate
import com.mbt925.realestate.feature.domain.FetchListingsEffect.Loading
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.Rule
import org.junit.Test

class FetchListingsReducerTest {

    @get:Rule
    val rule = TaskExecutorRule()

    private val state = ListingsState()

    @Test
    fun onLoading() = rule.runTest {
        val actualState = getReducer().invoke(state, Loading)
        assertTrue(actualState.loading)
    }

    @Test
    fun onSuccess() = rule.runTest {
        val expected = Success(listOf(realEstate), offsetDateTime)
        val actualState = getReducer().invoke(state, FetchListingsEffect.Success(expected))
        assertFalse(actualState.loading)
        assertEquals(expected, actualState.listings)
        assertNull(actualState.failure)
    }

    @Test
    fun onFailure() = rule.runTest {
        val expected = Failure.MissingNetwork
        val actualState = getReducer().invoke(state, FetchListingsEffect.Failure(expected))
        assertFalse(actualState.loading)
        assertEquals(expected, actualState.failure)
        assertNull(actualState.listings)
    }

    private fun getReducer() = fetchListingsReducer

}
