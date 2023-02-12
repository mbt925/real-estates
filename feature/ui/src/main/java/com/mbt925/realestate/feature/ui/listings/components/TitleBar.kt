package com.mbt925.realestate.feature.ui.listings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.mbt925.realestate.design.theme.IconSize
import com.mbt925.realestate.design.theme.SizeXXS
import com.mbt925.realestate.feature.ui.R
import com.mbt925.realestate.feature.ui.utils.TestTags

@Composable
internal fun TitleBar(
    modifier: Modifier,
    lastUpdate: String?,
) {
    Surface(
        modifier = Modifier
            .testTag(TestTags.Header)
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(SizeXXS),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SizeXXS),
            ) {
                Icon(
                    modifier = Modifier.size(IconSize * 2),
                    painter = painterResource(R.drawable.ic_header_logo),
                    contentDescription = stringResource(R.string.title),
                    tint = Color.Unspecified,
                )
                Text(
                    text = stringResource(R.string.title),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                )
            }
            if (lastUpdate != null) {
                Text(
                    text = stringResource(R.string.last_update, lastUpdate),
                )
            }
        }
    }
}
