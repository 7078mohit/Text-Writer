package com.storygenerator.features.text_writer.presentation.ui_main.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.storygenerator.core.events.navigator.interfaces.Navigator
import com.storygenerator.core.purchases.presentation.ui_main.navigation.PurchasesScreens
import com.storygenerator.core.purchases.presentation.viewmodels.PurchaseViewModel
import com.storygenerator.core.ui.utils.navigation.appNavComposable
import com.storygenerator.features.core.presentation.ui_main.screens.SettingScreens
import com.storygenerator.features.text_writer.presentation.ui_main.nav_graphs.WriterScreens
import kotlinx.coroutines.delay
import org.koin.compose.koinInject


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
fun WriterRootScreen(
    navigator: Navigator = koinInject(),
    purchaseViewModel: PurchaseViewModel = koinInject()

){

    val purchaseState by purchaseViewModel.state.collectAsStateWithLifecycle()

    var isPaywalShown by rememberSaveable{
        mutableStateOf(false)
    }

//    LaunchedEffect(Unit){
//        if (isPaywalShown)
//            return@LaunchedEffect
//
//        delay(5000)
//        if (purchaseState.currentSubscribedProduct == null){
//            //not subscribed to subscription
//            navigator.navigateTo(
//                route = PurchasesScreens.Root
//            )
//        }
//        isPaywalShown = true
//    }

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
