package com.mbt925.realestate.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.mbt925.realestate.core.decimal.formatter.DecimalFormatter
import com.mbt925.realestate.core.decimal.formatter.MoneyFormatter
import com.mbt925.realestate.design.phrase.phraseHolderSingle
import com.mbt925.realestate.design.platform.WindowSizeClass
import com.mbt925.realestate.design.platform.WindowWidthSizeClass
import com.mbt925.realestate.design.platform.rememberMapping
import com.mbt925.realestate.feature.api.ListingsState
import com.mbt925.realestate.feature.api.models.ListingsResult
import com.mbt925.realestate.feature.api.models.RealEstate
import com.mbt925.realestate.feature.ui.R
import com.mbt925.realestate.feature.ui.details.RealEstateDetailsScreenParam
import com.mbt925.realestate.feature.ui.models.FailureParam
import com.mbt925.realestate.feature.ui.models.ListingsScreenParam
import com.mbt925.realestate.feature.ui.models.RealEstateParam
import com.mbt925.realestate.feature.ui.utils.rememberDateTimeFormatter
import com.mbt925.realestate.feature.ui.utils.rememberDecimalFormatter
import com.mbt925.realestate.feature.ui.utils.rememberMoneyFormatter
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.StateFlow

interface UiStateMapper {

    @Composable
    fun map(state: StateFlow<ListingsState>): State<ListingsScreenParam>
    fun map(state: RealEstate?): RealEstateDetailsScreenParam?

    companion object {
        @Composable
        operator fun invoke(activity: MainActivity): UiStateMapper = UiStateMapperImpl(
            dateFormatter = rememberDateTimeFormatter(),
            moneyFormatter = rememberMoneyFormatter(),
            decimalFormatter = rememberDecimalFormatter(),
            windowSizeClass = WindowSizeClass.calculateFrom(activity),
        )
    }
}

private class UiStateMapperImpl(
    private val dateFormatter: DateTimeFormatter,
    private val moneyFormatter: MoneyFormatter,
    private val decimalFormatter: DecimalFormatter,
    private val windowSizeClass: WindowSizeClass,
) : UiStateMapper {

    @Composable
    override fun map(state: StateFlow<ListingsState>): State<ListingsScreenParam> {
        val state = state.collectAsState()
        val columns = windowSizeClass.rememberMapping {
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> 1
                WindowWidthSizeClass.Medium, WindowWidthSizeClass.Extended -> 2
            }
        }

        return remember {
            derivedStateOf {
                state.value.toListingsParam(
                    dateFormatter = dateFormatter,
                    moneyFormatter = moneyFormatter,
                    decimalFormatter = decimalFormatter
                ).copy(
                    columns = columns.value,
                )
            }
        }
    }

    override fun map(state: RealEstate?): RealEstateDetailsScreenParam? {
        return state?.toRealEstateParam(moneyFormatter, decimalFormatter)
            ?.let { param ->
                RealEstateDetailsScreenParam(
                    item = param,
                    hasVerticalLayout = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact,
                )
            }
    }

}

private fun ListingsState.toListingsParam(
    dateFormatter: DateTimeFormatter,
    moneyFormatter: MoneyFormatter,
    decimalFormatter: DecimalFormatter,
): ListingsScreenParam {

    return ListingsScreenParam(
        isLoading = loading,
        listing = listings?.let { success ->
            success.items.map {
                it.toRealEstateParam(
                    moneyFormatter = moneyFormatter,
                    decimalFormatter = decimalFormatter
                )
            }
        },
        failureParam = when (val failure = failure) {
            ListingsResult.Failure.MissingNetwork -> FailureParam.NoInternet()
            is ListingsResult.Failure.UnknownError -> FailureParam.UnknownError(
                reason = failure.message)
            null -> null
        },
        lastUpdate = listings?.let { success ->
            dateFormatter.format(success.dateTime)
        }
    )
}

private fun RealEstate.toRealEstateParam(
    moneyFormatter: MoneyFormatter,
    decimalFormatter: DecimalFormatter,
) = RealEstateParam(
    id = id,
    bedrooms = bedrooms?.let { decimalFormatter.format(it, 1) },
    city = city,
    area = phraseHolderSingle(R.string.m2, decimalFormatter.format(area, 2)),
    imageUrl = imageUrl,
    price = moneyFormatter.format(price, 0),
    agency = agency,
    propertyType = propertyType,
    rooms = rooms?.let { decimalFormatter.format(it, 1) },
)
