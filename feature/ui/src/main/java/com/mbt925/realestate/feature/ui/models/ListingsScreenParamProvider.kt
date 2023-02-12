package com.mbt925.realestate.feature.ui.models

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mbt925.realestate.design.phrase.phraseHolderRaw

internal class ListingsScreenParamProvider : PreviewParameterProvider<ListingsScreenParam> {
    private val item1 = RealEstateParam(
        id = "id1",
        bedrooms = "2.0",
        city = "Hamburg",
        area = phraseHolderRaw("54.87"),
        imageUrl = "imageUrl1",
        price = "123,45 €",
        agency = "agency1",
        propertyType = "type2",
        rooms = "4.5",
    )
    private val item2 = RealEstateParam(
        id = "id2",
        bedrooms = "4.5",
        city = "Berlin",
        area = phraseHolderRaw("23.17"),
        imageUrl = "imageUrl2",
        price = "123,974.5 €",
        agency = "agency2",
        propertyType = "type3",
        rooms = "3.0",
    )

    override val values = sequenceOf(
        ListingsScreenParam(
            isLoading = true,
        ),
        ListingsScreenParam(
            isLoading = false,
            lastUpdate = "Last update date",
            listing = listOf(item1, item2),
        ),
        ListingsScreenParam(
            isLoading = false,
            listing = listOf(item1, item2),
            columns = 2,
        ),
    )

}
