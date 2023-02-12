package com.mbt925.realestate.service.local

import androidx.datastore.core.DataStore
import com.mbt925.realestate.service.local.models.ListingsResultDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.invoke

interface RealEstatesLocalDataSource {

    suspend fun getListings(): ListingsResultDao?
    suspend fun setListings(data: ListingsResultDao)
}

internal class RealEstatesLocalDataSourceImpl(
    private val dataStore: DataStore<ListingsResultDao>,
    private val dispatcher: CoroutineDispatcher,
    ) : RealEstatesLocalDataSource {

    override suspend fun getListings(): ListingsResultDao? = dispatcher {
        dataStore.data.firstOrNull()
    }

    override suspend fun setListings(data: ListingsResultDao) = dispatcher {
        dataStore.updateData { data }
        Unit
    }

}
