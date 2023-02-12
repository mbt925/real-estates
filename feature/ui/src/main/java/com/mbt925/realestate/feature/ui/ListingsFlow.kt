@file:OptIn(ExperimentalAnimationApi::class)

package com.mbt925.realestate.feature.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mbt925.realestate.feature.ui.details.RealEstateDetailsScreen
import com.mbt925.realestate.feature.ui.details.RealEstateDetailsScreenParam
import com.mbt925.realestate.feature.ui.listings.ListingsScreen
import com.mbt925.realestate.feature.ui.models.ListingsScreenParam

@Composable
fun ListingsFlow(
    param: ListingsScreenParam,
    onGetDetailsParam: (String) -> RealEstateDetailsScreenParam?,
    onRetry: () -> Unit,
) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Destination.Listings.route,
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
        popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) },
    ) {
        composable(Destination.Listings.route) {
            ListingsScreen(
                param = param,
                onRetry = onRetry,
                onShowDetails = { id ->
                    navController.navigate("${Destination.Details.route}/$id")
                }
            )
        }
        composable(
            route = "${Destination.Details.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
        ) { backStackEntry ->
            val detailsParam = onGetDetailsParam(backStackEntry.arguments?.getString("id")!!)
            if (detailsParam != null) {
                RealEstateDetailsScreen(
                    param = detailsParam,
                    onBack = navController::popBackStack,
                )
            }
        }
    }
}
