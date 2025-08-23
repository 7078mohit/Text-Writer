package com.storygenerator.features.text_writer.presentation.ui_main.nav_graphs

import kotlinx.serialization.Serializable

@Serializable
sealed class WriterScreens {

    @Serializable
    data object Root : WriterScreens()

    @Serializable
    sealed class Home{
        @Serializable
        data object Root : Home()

        @Serializable
        data object Writer : Home()

        @Serializable
        data object History : Home()

        @Serializable
        data object Settings : Home()
    }

    @Serializable
    data object Preview : WriterScreens()

}