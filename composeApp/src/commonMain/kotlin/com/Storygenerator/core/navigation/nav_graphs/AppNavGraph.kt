package com.storygenerator.core.navigation.nav_graphs

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.storygenerator.core.purchases.presentation.ui_main.navigation.purchasesNavGraph
import com.storygenerator.features.core.presentation.ui_main.nav_graph.coreNavGraph
import com.storygenerator.features.text_writer.presentation.ui_main.nav_graphs.writerNavGraph


fun NavGraphBuilder.appNavGraph(
    scaffoldModifier: Modifier,
) {
//    authNavGraph(
//        scaffoldModifier = scaffoldModifier
//    )
    starterNavGraph(
        scaffoldModifier = scaffoldModifier
    )
    purchasesNavGraph(
        scaffoldModifier = scaffoldModifier
    )
    writerNavGraph()
    coreNavGraph(scaffoldModifier = scaffoldModifier)
}
