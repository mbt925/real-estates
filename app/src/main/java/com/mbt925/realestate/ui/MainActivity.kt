package com.mbt925.realestate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.mbt925.realestate.design.theme.RealEstatesAppTheme
import com.mbt925.realestate.feature.ui.ListingsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (savedInstanceState == null) {
            viewModel.fetchListings()
        }

        setContent {
            val uiStateMapper = UiStateMapper(this)
            val uiState = uiStateMapper.map(viewModel.state)

            RealEstatesAppTheme {
                ListingsFlow(
                    param = uiState.value,
                    onRetry = viewModel::fetchListings,
                    onGetDetailsParam = { id -> uiStateMapper.map(viewModel.getRealEstate(id)) }
                )
            }
        }
    }
}
