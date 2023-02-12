package com.mbt925.realestate.feature.api

import com.mbt925.realestate.core.data.DataAccess
import com.mbt925.realestate.feature.api.models.ListingsResult

interface RealEstatesRepository {

    fun getListings(): DataAccess<ListingsResult>
}
