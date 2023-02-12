package com.mbt925.realestate.networking

import com.mbt925.realestate.networking.models.ApiResponse
import java.net.HttpRetryException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import okio.IOException
import retrofit2.Call

@OptIn(ExperimentalSerializationApi::class)
fun <T> Call<T>.executeApi(): ApiResponse<T> {
    try {
        val response = execute()
        return if (response.isSuccessful) {
            return ApiResponse.success(
                data = response.body()
            )
        } else {
            try {
                response.errorBody()?.let { errorBody ->
                    ApiResponse.failure(
                        errorBody = errorBody.string(),
                    )
                } ?: ApiResponse.failure(
                    error = IllegalStateException(
                        "Unexpected error ${response.code()} with no error body"
                    )
                )
            } catch (e: MissingFieldException) {
                ApiResponse.failure(
                    error = IllegalStateException(
                        "Unexpected error ${response.code()} with invalid error body"
                    )
                )
            }
        }
    } catch (exception: IOException) {
        return ApiResponse.failure(error = exception)
    } catch (exception: HttpRetryException) {
        return ApiResponse.failure(error = exception)
    }
}
