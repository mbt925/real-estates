package com.mbt925.realestate.feature.ui.details

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mbt925.realestate.design.phrase.phraseHolderRaw
import com.mbt925.realestate.feature.ui.models.RealEstateParam

internal class RealEstateDetailsScreenParamProvider :
    PreviewParameterProvider<RealEstateDetailsScreenParam> {

    override val values = sequenceOf(
        RealEstateDetailsScreenParam(
            item = RealEstateParam(
                id = "id1",
                bedrooms = "2",
                city = "city",
                area = phraseHolderRaw("34.45 m²"),
                imageUrl = "imageUrl",
                price = "123,456 €",
                agency = "agency",
                propertyType = "type",
                rooms = "3",
            ),
            hasVerticalLayout = true,
        ),
        RealEstateDetailsScreenParam(
            item = RealEstateParam(
                id = "id1",
                bedrooms = "2",
                city = "city",
                area = phraseHolderRaw("34 m²"),
                imageUrl = "imageUrl",
                price = "123,456 €",
                agency = "agency",
                propertyType = "type",
                rooms = "3.5",
            ),
            hasVerticalLayout = false,
        ),
    )

}
