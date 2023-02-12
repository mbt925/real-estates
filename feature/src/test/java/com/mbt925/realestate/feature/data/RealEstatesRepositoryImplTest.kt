package com.mbt925.realestate.feature.data

import com.mbt925.realestate.core.test.TaskExecutorRule
import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.data.mapper.LocalDataAdapterImpl
import com.mbt925.realestate.feature.data.mapper.RemoteDataAdapterImpl
import com.mbt925.realestate.networking.models.ApiResponse
import com.mbt925.realestate.service.local.RealEstatesLocalDataSource
import com.mbt925.realestate.service.local.models.ListingsResultDao
import com.mbt925.realestate.service.remote.model.ListingsDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import org.junit.Rule
import org.junit.Test

class RealEstatesRepositoryImplTest {

    @get:Rule
    val rule = TaskExecutorRule()

    private val localDataSource = mockk<RealEstatesLocalDataSource> {
        coEvery { getListings() } returns ListingsResultDao(null)
        coEvery { setListings(any()) } just runs
    }
    private val remoteDataSource = mockk<RealEstatesRemoteDataSource> {
        coEvery { getListings() } returns ApiResponse.failure(Exception())
    }
    private val clockProvider = mockk<ClockProvider> {
        every { now() } returns offsetDateTime
    }
    private val localDataAdapter = LocalDataAdapterImpl()
    private val remoteDataAdapter = RemoteDataAdapterImpl(clockProvider)

    @Test
    fun onGetListings_callsRemote_getsFromCache() = rule.runTest {
        getRepo().getListings().getAndFetch().first()

        coVerify(exactly = 1) { localDataSource.getListings() }
    }

    @Test
    fun onGetListings_getsFromCache() = rule.runTest {
        coEvery { remoteDataSource.getListings() } returns ApiResponse.success(
            ListingsDto(listOf(realEstateDto))
        )
        coEvery { localDataSource.getListings() } returns ListingsResultDao(listOf(realEstateDao),
            offsetDateTime.toEpochSecond())

        val actual = getRepo().getListings().getAndFetch().first()
        val expected = ListingsResult.Success(listOf(realEstate), offsetDateTime)

        coVerify(exactly = 1) { localDataSource.getListings() }
        assertEquals(expected, actual)
    }

    @Test
    fun onGetListings_cachesData() = rule.runTest {
        coEvery { remoteDataSource.getListings() } returns ApiResponse.success(
            ListingsDto(listOf(realEstateDto))
        )

        getRepo().getListings().getAndFetch().collect()
        val expected = ListingsResultDao(listOf(realEstateDao), offsetDateTime.toEpochSecond())

        coVerify(exactly = 1) { localDataSource.setListings(expected) }
    }

    @Test
    fun onFailure_notCache() = rule.runTest {
        coEvery { remoteDataSource.getListings() } returns ApiResponse.failure(Exception("error"))

        val actual = getRepo().getListings().getAndFetch().first()
        val expected = ListingsResult.Failure.UnknownError("error")

        coVerify(exactly = 0) { localDataSource.setListings(any()) }
        assertEquals(actual, expected)
    }

    private fun getRepo() = RealEstatesRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource,
        localDataAdapter = localDataAdapter,
        remoteDataAdapter = remoteDataAdapter,
    )
}
