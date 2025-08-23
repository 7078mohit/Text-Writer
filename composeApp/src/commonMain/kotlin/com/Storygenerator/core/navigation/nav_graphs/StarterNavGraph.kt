package com.storygenerator.core.navigation.nav_graphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.Storygenerator.core.navigation.screens.StarterScreens
import com.storygenerator.core.purchases.presentation.ui_main.navigation.PurchasesScreens
import com.storygenerator.core.ui.composition_locals.LocalNavController
import com.storygenerator.core.ui.screens.WelcomeScreen
import com.storygenerator.core.ui.utils.navigation.appNavComposable

fun NavGraphBuilder.starterNavGraph(
    scaffoldModifier: Modifier,
){

    navigation<StarterScreens.Root>(
        startDestination = StarterScreens.WelcomeScreen,
    ) {
        appNavComposable<StarterScreens.WelcomeScreen> {
            val navController = LocalNavController.current
            WelcomeScreen(
                modifier = scaffoldModifier,
                onGetStartedClick = {
                    // Example of navigating with nav controller without global nav events
                    navController.navigate(
                        route = PurchasesScreens.SubscriptionScreen
                    )
                }
            )
        }
    }
}




















