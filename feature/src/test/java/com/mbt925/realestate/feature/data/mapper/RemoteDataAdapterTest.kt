package com.mbt925.realestate.feature.data.mapper

import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.data.ClockProvider
import com.mbt925.realestate.feature.data.offsetDateTime
import com.mbt925.realestate.feature.data.realEstate
import com.mbt925.realestate.feature.data.realEstateDto
import com.mbt925.realestate.networking.models.ApiResponse
import com.mbt925.realestate.service.remote.model.ListingsDto
import io.mockk.every
import io.mockk.mockk
import java.io.IOException
import kotlin.test.assertEquals
import org.junit.Test

class RemoteDataAdapterTest {

    private val clockProvider = mockk<ClockProvider> {
        every { now() } returns offsetDateTime
    }

    @Test
    fun success() {
        val adapter = getProvider()
        assertEquals(
            ListingsResult.Success(listOf(realEstate), offsetDateTime),
            adapter.mapListings(ApiResponse.success(ListingsDto(listOf(realEstateDto)))),
        )
    }

    @Test
    fun failure() {
        val domain = listOf(
            ListingsResult.Failure.UnknownError("error"),
            ListingsResult.Failure.MissingNetwork,
            ListingsResult.Failure.UnknownError("message"),
        )
        val ioException = IOException()
        val unknownException = Throwable("message")
        val dtos = listOf(
            ApiResponse.failure("{\"code\":\"code\", \"message\":\"error\", \"data\":null}"),
            ApiResponse.failure(ioException),
            ApiResponse.failure(unknownException),
        )

        val adapter = getProvider()
        domain.forEachIndexed { index, data ->
            val dto = dtos[index]
            assertEquals(
                data,
                adapter.mapListings(dto),
            )
        }
    }

    private fun getProvider() = RemoteDataAdapterImpl(
        clockProvider = clockProvider,
    )

}
