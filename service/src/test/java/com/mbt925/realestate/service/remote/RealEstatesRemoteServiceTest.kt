package com.mbt925.realestate.service.remote

import com.mbt925.realestate.service.remote.model.ListingsDto
import com.mbt925.realestate.service.remote.model.RealEstateDto
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class RealEstatesRemoteServiceTest {

    @Test
    fun getListings() {

        val expected = ListingsDto(listOf(item1, item2))

        val payload = """
            { "items": [{
              "bedrooms": 2,
              "city": "city1",
              "id": 1,
              "area": 54.1,
              "url": "imageUrl1",
              "price": 12312343.1,
              "professional": "prof1",
              "propertyType": "propertyType1",
              "offerType": 1,
              "rooms": 4.5
          },
          {
              "bedrooms": 3,
              "city": "city2",
              "id": 2,
              "area": 51.3,
              "url": "imageUrl2",
              "price": 124568343.8,
              "professional": "prof2",
              "propertyType": "propertyType2",
              "offerType": 2,
              "rooms": 5
          }],
          "totalCount": 2
        }
        """

        assertEquals(
            expected,
            json.decodeFromString(ListingsDto.serializer(), payload),
        )
    }

    private val item1 = RealEstateDto(
        id = 1,
        bedrooms = 2.0,
        city = "city1",
        area = 54.1,
        imageUrl = "imageUrl1",
        price = 12312343.1,
        agency = "prof1",
        propertyType = "propertyType1",
        rooms = 4.5,
    )

    private val item2 = RealEstateDto(
        id = 2,
        bedrooms = 3.0,
        city = "city2",
        area = 51.3,
        imageUrl = "imageUrl2",
        price = 124568343.8,
        agency = "prof2",
        propertyType = "propertyType2",
        rooms = 5.0,
    )

    private val json = Json {
        ignoreUnknownKeys = true
    }
}
