package com.mbt925.realestate.design.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.mbt925.realestate.design.theme.SizeM
import com.mbt925.realestate.design.theme.SizeXS

@Composable
fun NavIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    onClick: (() -> Unit),
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier
                .padding(SizeXS)
                .size(SizeM),
            painter = painterResource(iconResId),
            contentDescription = null,
        )
    }
}
