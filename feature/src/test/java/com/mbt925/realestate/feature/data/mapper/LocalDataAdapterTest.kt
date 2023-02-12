package com.mbt925.realestate.feature.data.mapper

import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.data.offsetDateTime
import com.mbt925.realestate.feature.data.realEstate
import com.mbt925.realestate.feature.data.realEstateDao
import com.mbt925.realestate.service.local.models.ListingsResultDao
import kotlin.test.assertEquals
import org.junit.Test

class LocalDataAdapterTest {

    @Test
    fun dao_to_domain() {
        val data = listOf(
            ListingsResult.Success(listOf(realEstate), offsetDateTime),
            ListingsResult.Failure.MissingNetwork,
        )
        val daos = listOf(
            ListingsResultDao(listOf(realEstateDao), offsetDateTime.toEpochSecond()),
            null,
        )

        val adapter = getProvider()
        data.forEachIndexed { index, d ->
            val dao = daos[index]
            assertEquals(
                dao,
                adapter.map(d),
            )
        }
    }

    @Test
    fun domain_to_dao() {
        val daos = listOf(
            ListingsResultDao(listOf(realEstateDao), offsetDateTime.toEpochSecond()),
            ListingsResultDao(null),
        )
        val data = listOf(
            ListingsResult.Success(listOf(realEstate), offsetDateTime),
            null,
        )

        val adapter = getProvider()
        data.forEachIndexed { index, d ->
            val dao = daos[index]
            assertEquals(
                d,
                adapter.map(dao),
            )
        }
    }

    private fun getProvider() = LocalDataAdapterImpl()

}
