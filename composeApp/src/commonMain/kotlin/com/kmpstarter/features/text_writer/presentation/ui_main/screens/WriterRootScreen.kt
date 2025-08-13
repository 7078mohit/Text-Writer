package com.kmpstarter.features.text_writer.presentation.ui_main.screens

import androidx.collection.emptyLongSet
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kmpstarter.core.ui.utils.navigation.appNavComposable
import com.kmpstarter.features.core.presentation.ui_main.screens.SettingScreens
import com.kmpstarter.features.text_writer.presentation.ui_main.nav_graphs.WriterScreens


private data class BottomBarItem(
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String,
    val route: WriterScreens.Home
)

private val bottomBarItems = listOf<BottomBarItem>(
    BottomBarItem(
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        label = "Home",
        route = WriterScreens.Home.Writer
    ),

    BottomBarItem(
        icon = Icons.Outlined.History,
        selectedIcon = Icons.Filled.History,
        label = "History",
        route = WriterScreens.Home.History
    ),

    BottomBarItem(
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        label = "Settings",
        route = WriterScreens.Home.Settings
    )
)


@Composable
fun WriterRootScreen(){

    val navHostController = rememberNavController()

    WriterRootScreenContent(navHostController)
}

@Composable
fun WriterRootScreenContent(navHostController: NavHostController, modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                bottomBarItems.forEachIndexed { index, item ->

                    val backStackEntry = navHostController.currentBackStackEntryAsState()
                    val currentRoute = backStackEntry.value?.destination

                    val isSelected = if (currentRoute == null) false else {
                        val currentRouteString = currentRoute.route?.split(".")?.last()
                        if (currentRouteString == null) false else {
                            currentRouteString == item.route::class.simpleName
                        }
                    }


                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navHostController.navigate(item.route){
                                popUpTo(
                                    navHostController.graph.findStartDestination().id
                                ){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            AnimatedContent(
                                targetState = isSelected,
                                transitionSpec = {
                                    scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut()
                                }
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = item.selectedIcon,
                                        contentDescription = null
                                    )
                                }
                                    else{
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = null
                                        )
                                    }
                                }
                        },
                        label = {
                            Text(text = item.label)
                        }
                    )
                }
            }
        }
    )
    { paddingValues: PaddingValues ->

        NavHost(navController = navHostController, startDestination = WriterScreens.Home.Writer) {
            appNavComposable<WriterScreens.Home.Writer> {
                WriterScreen(paddingValues = paddingValues)
            }
            appNavComposable<WriterScreens.Home.History> {
                HistoryScreen()
            }
            appNavComposable<WriterScreens.Home.Settings> {
                SettingScreens()
            }
        }
    }
}
