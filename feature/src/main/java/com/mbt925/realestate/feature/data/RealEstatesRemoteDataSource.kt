package com.mbt925.realestate.feature.data

import com.mbt925.realestate.networking.executeApi
import com.mbt925.realestate.networking.models.ApiResponse
import com.mbt925.realestate.service.remote.RealEstatesRemoteService
import com.mbt925.realestate.service.remote.model.ListingsDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke

interface RealEstatesRemoteDataSource {

    suspend fun getListings(): ApiResponse<ListingsDto>
}

internal class RealEstatesRemoteDataSourceImpl(
    private val service: RealEstatesRemoteService,
    private val dispatcher: CoroutineDispatcher,
) : RealEstatesRemoteDataSource {

    override suspend fun getListings() = dispatcher {
        service.getListings().executeApi()
    }
}
