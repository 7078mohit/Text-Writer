package com.kmpstarter.features.text_writer.presentation.ui_main.nav_graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.kmpstarter.core.ui.utils.navigation.appNavComposable
import com.kmpstarter.features.text_writer.presentation.ui_main.screens.PreviewScreen
import com.kmpstarter.features.text_writer.presentation.ui_main.screens.WriterRootScreen

fun NavGraphBuilder.writerNavGraph(){

    navigation<WriterScreens.Root>(
        startDestination = WriterScreens.Home.Root
    ){

        appNavComposable<WriterScreens.Home.Root> {
            WriterRootScreen()
        }

        appNavComposable<WriterScreens.Preview> {
            PreviewScreen()
        }


    }


}