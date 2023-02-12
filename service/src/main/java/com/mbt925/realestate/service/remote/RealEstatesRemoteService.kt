package com.mbt925.realestate.service.remote

import com.mbt925.realestate.service.remote.model.ListingsDto
import retrofit2.Call
import retrofit2.http.GET

interface RealEstatesRemoteService {

    @GET("listings.json")
    fun getListings(): Call<ListingsDto>

}
