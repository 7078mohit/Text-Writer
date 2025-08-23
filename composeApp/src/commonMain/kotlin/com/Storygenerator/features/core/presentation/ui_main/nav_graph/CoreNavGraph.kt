package com.storygenerator.features.core.presentation.ui_main.nav_graph

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.storygenerator.core.ui.utils.navigation.appNavComposable
import com.storygenerator.features.core.presentation.ui_main.screens.OnboardingScreen
import com.storygenerator.features.core.presentation.ui_main.screens.SplashScreen

fun NavGraphBuilder.coreNavGraph(
    scaffoldModifier : Modifier = Modifier
){
    appNavComposable<CoreScreens.Splash> {
        SplashScreen()
    }

    appNavComposable<CoreScreens.OnBoarding> {
        OnboardingScreen(scaffoldModifier = scaffoldModifier)
    }
}