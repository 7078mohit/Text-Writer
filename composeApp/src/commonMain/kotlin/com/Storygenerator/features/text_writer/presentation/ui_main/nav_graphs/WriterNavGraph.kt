package com.storygenerator.features.text_writer.presentation.ui_main.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.storygenerator.core.ui.utils.navigation.appNavComposable
import com.storygenerator.features.text_writer.presentation.ui_main.screens.PreviewScreen
import com.storygenerator.features.text_writer.presentation.ui_main.screens.WriterRootScreen

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