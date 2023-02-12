package com.mbt925.realestate.service.di

import com.mbt925.realestate.service.local.RealEstatesLocalDataSource
import com.mbt925.realestate.service.local.RealEstatesLocalDataSourceImpl
import com.mbt925.realestate.service.local.listingResultDataSource
import com.mbt925.realestate.service.remote.RealEstatesRemoteService
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {

    single { get<Retrofit>().create(RealEstatesRemoteService::class.java) }

    factory<RealEstatesLocalDataSource> {
        RealEstatesLocalDataSourceImpl(
            dataStore = androidContext().listingResultDataSource,
            dispatcher = Dispatchers.IO,
        )
    }

}
