package com.mbt925.realestate.feature.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mbt925.realestate.design.phrase.phraseHolderRaw
import com.mbt925.realestate.design.theme.RealEstatesAppTheme
import com.mbt925.realestate.feature.ui.listings.ListingsScreen
import com.mbt925.realestate.feature.ui.models.ListingsScreenParam
import com.mbt925.realestate.feature.ui.models.RealEstateParam
import com.mbt925.realestate.feature.ui.utils.TestTags
import org.junit.Rule
import org.junit.Test

class ListingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun isLoadingShown() {
        initScreen()

        composeTestRule
            .onNodeWithTag(TestTags.Loading)
            .assertIsDisplayed()
    }

    @Test
    fun isHeaderShown() {
        initScreen()

        composeTestRule
            .onNodeWithTag(TestTags.Header)
            .assertIsDisplayed()
    }

    @Test
    fun onCompactScreen_isOneColumnUsed() {
        initScreen(compactListingsParam)

        composeTestRule
            .onNodeWithTag(TestTags.ListingsGridSingleColumn)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTags.Header)
            .assertIsDisplayed()
    }

    @Test
    fun onExtendedScreen_isTwoColumnsUsed() {
        initScreen(extendedListingsParam)

        composeTestRule
            .onNodeWithTag(TestTags.ListingsGridMultiColumn)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTags.Header)
            .assertIsDisplayed()
    }

    @Test
    fun onListingsItem_areItemsShown() {
        initScreen(compactListingsParam)

        composeTestRule
            .onNodeWithTag("${TestTags.RealEstateItem}_${item1.id}")
            .assertIsDisplayed()
    }

    private val item1 = RealEstateParam(
        id = "id1",
        bedrooms = "2.0",
        city = "Hamburg",
        area = phraseHolderRaw("54.87"),
        imageUrl = "imageUrl1",
        price = "123,345.12",
        agency = "agency1",
        propertyType = "type2",
        rooms = "4.5",
    )

    private val compactListingsParam = ListingsScreenParam(
        isLoading = false,
        listing = listOf(item1),
        columns = 1,
    )

    private val extendedListingsParam = ListingsScreenParam(
        isLoading = false,
        listing = listOf(item1),
        columns = 2,
    )

    private fun initScreen(param: ListingsScreenParam = ListingsScreenParam()) {
        composeTestRule.setContent {
            RealEstatesAppTheme {
                ListingsScreen(
                    param = param,
                    onRetry = {},
                    onShowDetails = {},
                )
            }
        }
    }
}
