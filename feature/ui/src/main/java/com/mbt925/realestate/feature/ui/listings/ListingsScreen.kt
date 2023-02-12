package com.mbt925.realestate.feature.ui.listings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.mbt925.realestate.design.component.Spinner
import com.mbt925.realestate.design.overlay.DefaultSnackBar
import com.mbt925.realestate.design.preview.ScreenPreviews
import com.mbt925.realestate.design.theme.RealEstatesAppTheme
import com.mbt925.realestate.design.theme.SizeM
import com.mbt925.realestate.design.theme.SizeS
import com.mbt925.realestate.design.theme.SizeXXXXL
import com.mbt925.realestate.feature.ui.R
import com.mbt925.realestate.feature.ui.listings.components.RealEstateItem
import com.mbt925.realestate.feature.ui.listings.components.TitleBar
import com.mbt925.realestate.feature.ui.models.FailureParam
import com.mbt925.realestate.feature.ui.models.FailureParam.UnknownError
import com.mbt925.realestate.feature.ui.models.ListingsScreenParam
import com.mbt925.realestate.feature.ui.models.ListingsScreenParamProvider
import com.mbt925.realestate.feature.ui.utils.TestTags

@Composable
internal fun ListingsScreen(
    param: ListingsScreenParam,
    onShowDetails: (String) -> Unit,
    onRetry: () -> Unit,
) {

    val scaffoldState = rememberScaffoldState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = param.isLoading,
        onRefresh = onRetry,
    )
    val gridState = rememberLazyGridState()
    val headerScrollOffset = remember {
        derivedStateOf {
            if (gridState.firstVisibleItemIndex == 0) {
                gridState.firstVisibleItemScrollOffset.toFloat() / 2
            } else 0f
        }
    }

    HandleErrors(
        scaffoldState = scaffoldState,
        failureParam = param.failureParam,
    )

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .testTag(
                        if (param.columns == 1) TestTags.ListingsGridSingleColumn
                        else TestTags.ListingsGridMultiColumn)
                    .padding(contentPadding),
                state = gridState,
                columns = GridCells.Fixed(param.columns),
                verticalArrangement = Arrangement.spacedBy(SizeS),
                horizontalArrangement = Arrangement.spacedBy(SizeS),
            ) {

                item(span = { GridItemSpan(maxLineSpan) }) {
                    TitleBar(
                        modifier = Modifier
                            .padding(SizeM)
                            .padding(WindowInsets.statusBars.asPaddingValues())
                            .graphicsLayer { translationY = headerScrollOffset.value },
                        lastUpdate = param.lastUpdate,
                    )
                }

                if (param.isLoading) {
                    item { LoadingBox() }
                }

                if (param.listing != null) {
                    param.listing.forEach {
                        item(key = it.id) {
                            RealEstateItem(
                                modifier = Modifier
                                    .testTag("${TestTags.RealEstateItem}_${it.id}")
                                    .padding(horizontal = SizeM)
                                    .animateItemPlacement(),
                                param = it,
                                onClick = { onShowDetails(it.id) },
                            )
                        }
                    }
                }

                item {
                    Spacer(Modifier
                        .height(SizeXXXXL)
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                    )
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(TopCenter),
                refreshing = param.isLoading,
                state = pullRefreshState,
            )

            DefaultSnackBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                contentModifier = Modifier
                    .padding(SizeM)
                    .navigationBarsPadding(),
                hostState = scaffoldState.snackbarHostState,
                onAction = {
                    when (param.failureParam) {
                        else -> onRetry()
                    }
                },
            )
        }
    }
}

@Composable
private fun LoadingBox() {
    Box(
        modifier = Modifier
            .testTag(TestTags.Loading)
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .height(SizeXXXXL),
    ) {
        Spinner(
            modifier = Modifier.align(Center),
        )
    }
}

@Composable
private fun HandleErrors(
    scaffoldState: ScaffoldState,
    failureParam: FailureParam?,
) {
    if (failureParam != null) {
        val message = when (failureParam) {
            is UnknownError -> stringResource(failureParam.message, failureParam.reason)
            else -> stringResource(failureParam.message)
        }
        val actionLabel = stringResource(R.string.retry)

        LaunchedEffect(failureParam) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Indefinite,
            )
        }
    } else {
        LaunchedEffect(Unit) {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
}

@ScreenPreviews
@Composable
private fun ListingsScreenPreview(
    @PreviewParameter(ListingsScreenParamProvider::class) param: ListingsScreenParam,
) {
    RealEstatesAppTheme {
        ListingsScreen(
            param = param,
            onShowDetails = {},
            onRetry = {},
        )
    }
}
