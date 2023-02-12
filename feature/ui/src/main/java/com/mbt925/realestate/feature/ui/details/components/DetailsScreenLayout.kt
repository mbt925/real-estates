package com.mbt925.realestate.feature.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mbt925.realestate.design.theme.SizeM

@Composable
internal fun DetailsScreenLayout(
    isVertical: Boolean,
    navBar: @Composable (Modifier) -> Unit,
    imageBox: @Composable (Modifier) -> Unit,
    detailsBox: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
    ) { contentPadding ->
        if (isVertical) {
            Column(Modifier.padding(contentPadding)) {
                Box(Modifier.weight(1f)) {
                    imageBox(Modifier.fillMaxWidth())
                    navBar(Modifier
                        .padding(SizeM)
                        .statusBarsPadding())
                }
                detailsBox(Modifier.weight(1f))
            }
        } else {
            Row(
                modifier = Modifier.padding(contentPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.weight(1f)) {
                    imageBox(Modifier.fillMaxWidth())
                    navBar(Modifier
                        .padding(SizeM)
                        .statusBarsPadding())
                }
                detailsBox(Modifier.weight(1f))
            }
        }
    }
}
