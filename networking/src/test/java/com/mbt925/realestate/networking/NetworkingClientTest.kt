package com.mbt925.realestate.networking

import com.mbt925.realestate.networking.models.ApiError
import com.mbt925.realestate.networking.models.ApiResponse
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class NetworkingClientTest {

    @Test
    fun onSuccess_returnsSuccessWithData() {
        val call: Call<A> = mockk {
            every { execute() } returns Response.success(200, A)
        }
        val actual = call.executeApi()
        val expected = ApiResponse.success(A)

        assertEquals(expected, actual)
    }

    @Test
    fun onFailureWithErrorBody_returnsKnownFailure() {
        val code = "error_code"
        val message = "error_message"
        val data = "extra_data"
        val errorBody = """{"code": "$code", "message":"$message", "data": "$data"}"""
        val call: Call<A> = mockk {
            every { execute() } returns Response.error(400, errorBody.toResponseBody("application/json".toMediaType()))
        }
        val actual = call.executeApi()
        val expected = ApiResponse.Failure.Known(ApiError(code, message, data))

        assertEquals(expected, actual)
    }

    @Test
    fun onUnknownFailure_returnsUnknownFailure() {
        val errorCode = 401
        val errorBody = "{}"
        val call: Call<A> = mockk {
            every { execute() } returns Response.error(errorCode, errorBody.toResponseBody(null))
        }
        val actual = call.executeApi()
        val actualException = (actual as ApiResponse.Failure.Unknown).error as java.lang.IllegalStateException
        assertTrue(actualException.message?.contains("$errorCode") == true)
    }

    private object A

}
