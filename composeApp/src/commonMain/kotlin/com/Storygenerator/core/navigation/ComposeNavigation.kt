package com.storygenerator.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import com.storygenerator.core.events.navigator.interfaces.Navigator
import com.storygenerator.core.events.navigator.utils.handleNavigationAction
import com.storygenerator.core.events.utils.ObserveAsEvents
import com.storygenerator.core.navigation.nav_graphs.appNavGraph
import com.storygenerator.core.ui.composition_locals.LocalNavController
import com.storygenerator.features.core.presentation.ui_main.nav_graph.CoreScreens
import org.koin.compose.koinInject


@Composable
fun ComposeNavigation(
    scaffoldModifier: Modifier = Modifier,
    navigator: Navigator = koinInject(),
    navController: NavHostController = rememberNavController(),
) {
    NavigationSideEffects(navigator, navController)

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = CoreScreens.Splash
        ) {
            appNavGraph(
                scaffoldModifier = scaffoldModifier
            )

        }
    }
}

@Composable
private fun NavigationSideEffects(
    navigator: Navigator,
    navController: NavHostController,
) {
    ObserveAsEvents(
        flow = navigator.navigationActions
    ) { action ->
        navController.handleNavigationAction(
            action = action
        )
    }
}