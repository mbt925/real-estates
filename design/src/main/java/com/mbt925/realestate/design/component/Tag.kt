package com.mbt925.realestate.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mbt925.realestate.design.theme.SizeS
import com.mbt925.realestate.design.theme.SizeXXS
import com.mbt925.realestate.design.theme.SizeXXXS

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)?,
) {
    Button(
        onClick = onClick,
    ) {
        Box(
            modifier = modifier
                .background(
                    color = MaterialTheme.colors.secondaryVariant,
                    shape = RoundedCornerShape(SizeS),
                )
                .padding(
                    vertical = SizeXXXS,
                    horizontal = SizeXXS,
                ),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
            )
        }
    }
}
