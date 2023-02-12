package com.mbt925.realestate.networking.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
val networkingModule = module {

    single {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl("https://gsl-apps-technical-test.dignp.com/")
            .client(client)
            .addConverterFactory(get())
            .build()
    }

    single {
        Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    }

}
