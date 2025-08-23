package com.storygenerator.features.core.presentation.ui_main.nav_graph

import kotlinx.serialization.Serializable

@Serializable
sealed class CoreScreens {
    @Serializable
    data object OnBoarding : CoreScreens()

    @Serializable
    data object Splash : CoreScreens()

}