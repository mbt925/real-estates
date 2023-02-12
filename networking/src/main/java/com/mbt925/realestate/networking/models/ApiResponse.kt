package com.mbt925.realestate.networking.models

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

sealed interface ApiResponse<out T> {

    data class Success<T>(val data: T?) : ApiResponse<T>

    sealed interface Failure : ApiResponse<Nothing> {

        data class Known(val error: ApiError) : Failure
        data class Unknown(val error: Throwable) : Failure

    }

    companion object {
        fun <T> success(data: T?) = Success(data)

        fun failure(
            errorBody: String,
        ): ApiResponse<Nothing> = Failure.Known(Json.decodeFromString(errorBody))

        fun failure(
            error: Throwable,
        ): ApiResponse<Nothing> = Failure.Unknown(error)
    }
}
