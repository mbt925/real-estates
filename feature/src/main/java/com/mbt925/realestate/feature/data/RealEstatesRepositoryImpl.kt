package com.mbt925.realestate.feature.data

import com.mbt925.realestate.core.data.DataAccess
import com.mbt925.realestate.core.data.invoke
import com.mbt925.realestate.feature.api.RealEstatesRepository
import com.mbt925.realestate.feature.data.mapper.LocalDataAdapter
import com.mbt925.realestate.feature.data.mapper.RemoteDataAdapter
import com.mbt925.realestate.service.local.RealEstatesLocalDataSource

internal class RealEstatesRepositoryImpl(
    private val localDataSource: RealEstatesLocalDataSource,
    private val remoteDataSource: RealEstatesRemoteDataSource,
    private val remoteDataAdapter: RemoteDataAdapter,
    private val localDataAdapter: LocalDataAdapter,
) : RealEstatesRepository {

    override fun getListings() = DataAccess(
        get = {
            localDataSource.getListings()?.let {
                localDataAdapter.map(it)
            }
        },
        save = { data ->
            val success = data?.let(localDataAdapter::map)?.let {
                localDataSource.setListings(it)
                true
            } ?: false
            success
        },
        fetch = {
            val result = remoteDataSource.getListings()
            remoteDataAdapter.mapListings(result)
        }
    )

}
