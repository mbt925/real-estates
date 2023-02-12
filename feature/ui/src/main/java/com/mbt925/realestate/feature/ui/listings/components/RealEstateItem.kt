package com.mbt925.realestate.feature.ui.listings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mbt925.realestate.design.component.IconText
import com.mbt925.realestate.design.component.Tag
import com.mbt925.realestate.design.phrase.resolve
import com.mbt925.realestate.design.theme.SizeS
import com.mbt925.realestate.design.theme.SizeXS
import com.mbt925.realestate.design.theme.SizeXXS
import com.mbt925.realestate.feature.ui.R
import com.mbt925.realestate.feature.ui.models.RealEstateParam

@Composable
internal fun RealEstateItem(
    modifier: Modifier = Modifier,
    param: RealEstateParam,
    onClick: () -> Unit,
) {
    val surfaceShape = MaterialTheme.shapes.medium

    Surface(
        modifier = modifier
            .shadow(SizeXXS, surfaceShape, clip = true)
            .background(
                color = MaterialTheme.colors.surface,
                shape = surfaceShape,
            ),
        onClick = onClick,
    ) {
        Column {
            TitleBar(
                agency = param.agency,
                imageUrl = param.imageUrl,
            )
            Column(
                modifier = Modifier.padding(SizeS),
                verticalArrangement = Arrangement.spacedBy(SizeXXS),
            ) {
                IconText(
                    text = param.city,
                    iconResId = R.drawable.ic_pin,
                    onClick = null,
                )

                Tag(
                    text = param.propertyType,
                    onClick = null,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SizeXS),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = param.price,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                    )
                    IconText(
                        text = param.area.resolve(),
                        iconResId = R.drawable.ic_size,
                        onClick = null,
                    )
                    if (param.rooms != null) {
                        IconText(
                            text = param.rooms,
                            iconResId = R.drawable.ic_room,
                            onClick = null,
                        )
                    }
                    if (param.bedrooms != null) {
                        IconText(
                            text = param.bedrooms,
                            iconResId = R.drawable.ic_bedroom,
                            onClick = null,
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun TitleBar(
    agency: String,
    imageUrl: String?,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .blur(radius = 0.7.dp),
            model = imageUrl,
            contentDescription = agency,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_home),
            error = painterResource(R.drawable.ic_home),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeS),
            text = agency,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
        )

    }
}
