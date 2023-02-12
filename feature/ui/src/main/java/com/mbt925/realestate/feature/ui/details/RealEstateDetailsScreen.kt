package com.mbt925.realestate.feature.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mbt925.realestate.design.component.IconText
import com.mbt925.realestate.design.component.NavIcon
import com.mbt925.realestate.design.component.Tag
import com.mbt925.realestate.design.phrase.resolve
import com.mbt925.realestate.design.preview.ScreenPreviews
import com.mbt925.realestate.design.theme.SizeS
import com.mbt925.realestate.design.theme.SizeXS
import com.mbt925.realestate.feature.ui.R
import com.mbt925.realestate.feature.ui.details.components.DetailsScreenLayout

@Composable
internal fun RealEstateDetailsScreen(
    param: RealEstateDetailsScreenParam,
    onBack: () -> Unit,
) {
    val navBar: @Composable (Modifier) -> Unit = { modifier ->
        NavIcon(
            modifier = modifier,
            iconResId = R.drawable.ic_back,
            onClick = onBack,
        )
    }
    val imageBox: @Composable (Modifier) -> Unit = { modifier ->
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .shadow(1.dp),
            model = param.item.imageUrl,
            contentDescription = param.item.agency,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_home),
            error = painterResource(R.drawable.ic_home),
        )
    }
    val detailsBox: @Composable (Modifier) -> Unit = { modifier ->
        Column(
            modifier = modifier.padding(SizeS),
            verticalArrangement = Arrangement.spacedBy(SizeXS),
        ) {

            Text(
                text = param.item.agency,
                style = MaterialTheme.typography.h5,
            )

            IconText(
                text = param.item.city,
                iconResId = R.drawable.ic_pin,
                onClick = null,
            )

            Tag(
                text = param.item.propertyType,
                onClick = null,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SizeXS),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = param.item.price,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                )
                IconText(
                    text = param.item.area.resolve(),
                    iconResId = R.drawable.ic_size,
                    onClick = null,
                )
                if (param.item.rooms != null) {
                    IconText(
                        text = param.item.rooms,
                        iconResId = R.drawable.ic_room,
                        onClick = null,
                    )
                }
                if (param.item.bedrooms != null) {
                    IconText(
                        text = param.item.bedrooms,
                        iconResId = R.drawable.ic_bedroom,
                        onClick = null,
                    )
                }
            }
        }
    }

    DetailsScreenLayout(
        isVertical = param.hasVerticalLayout,
        navBar = navBar,
        imageBox = imageBox,
        detailsBox = detailsBox,
    )
}

@ScreenPreviews
@Composable
internal fun RealEstateDetailsScreenPreview(
    @PreviewParameter(
        RealEstateDetailsScreenParamProvider::class) param: RealEstateDetailsScreenParam,
) {
    MaterialTheme {
        RealEstateDetailsScreen(
            param = param,
            onBack = {},
        )
    }
}
