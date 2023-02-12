package com.mbt925.realestate.feature.di

import com.mbt925.realestate.feature.api.RealEstatesModel
import com.mbt925.realestate.feature.api.RealEstatesRepository
import com.mbt925.realestate.feature.data.ClockProvider
import com.mbt925.realestate.feature.data.ClockProviderImpl
import com.mbt925.realestate.feature.data.RealEstatesRemoteDataSource
import com.mbt925.realestate.feature.data.RealEstatesRemoteDataSourceImpl
import com.mbt925.realestate.feature.data.RealEstatesRepositoryImpl
import com.mbt925.realestate.feature.data.mapper.LocalDataAdapter
import com.mbt925.realestate.feature.data.mapper.LocalDataAdapterImpl
import com.mbt925.realestate.feature.data.mapper.RemoteDataAdapter
import com.mbt925.realestate.feature.data.mapper.RemoteDataAdapterImpl
import com.mbt925.realestate.feature.domain.RealEstatesContextImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val featureModule = module {

    factory<RealEstatesRepository> {
        RealEstatesRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
            remoteDataAdapter = get(),
            localDataAdapter = get(),
        )
    }
    factory<RealEstatesRemoteDataSource> {
        RealEstatesRemoteDataSourceImpl(
            service = get(),
            dispatcher = Dispatchers.IO,
        )
    }

    single<ClockProvider> { ClockProviderImpl() }
    factory<LocalDataAdapter> { LocalDataAdapterImpl() }
    factory<RemoteDataAdapter> { RemoteDataAdapterImpl(clockProvider = get()) }

    factory<RealEstatesModel> {
        RealEstatesContextImpl(realEstatesRepository = get())
    }

}
