package com.mbt925.realestate.feature.data

import com.mbt925.realestate.core.test.TaskExecutorRule
import com.mbt925.realestate.service.remote.RealEstatesRemoteService
import com.mbt925.realestate.service.remote.model.ListingsDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RealEstatesRemoteDataSourceImplTest {

    @get:Rule
    val rule = TaskExecutorRule()

    private val service = mockk<RealEstatesRemoteService> {
        every { getListings() } returns mockCall(ListingsDto(listOf(realEstateDto)))
    }

    @Test
    fun onFetchData_callsService() = rule.runTest {
        getDataSource().getListings()

        coVerify { service.getListings() }
    }

    private fun <T> mockCall(data: T): retrofit2.Call<T> = mockk {
        coEvery { execute() } returns Response.success(data)
    }

    private fun getDataSource() = RealEstatesRemoteDataSourceImpl(
        service = service,
        dispatcher = rule.dispatcher,
    )

}
