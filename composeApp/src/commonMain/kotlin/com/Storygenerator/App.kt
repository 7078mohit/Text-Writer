package com.storygenerator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.storygenerator.core.navigation.ComposeNavigation
import com.storygenerator.core.datastore.theme.ThemeDataStore
import com.storygenerator.core.events.controllers.SnackbarController
import com.storygenerator.core.events.utils.ObserveAsEvents
import com.storygenerator.features.text_writer.domain.repository.WriterRepository
import com.storygenerator.theme.ApplicationTheme

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App() {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val writerRepository : WriterRepository = koinInject()


    GlobalSideEffects(snackbarHostState = snackbarHostState)
    MainApp(snackbarHostState = snackbarHostState)
}

@Composable
private fun MainApp(
    snackbarHostState: SnackbarHostState,
    themeDataStore: ThemeDataStore = koinInject(),
) {
    val currentThemeMode by themeDataStore.themeMode.collectAsState(
        initial = ThemeDataStore.DEFAULT_THEME_MODE
    )
    val currentDynamicColor by themeDataStore.dynamicColor.collectAsState(
        initial = ThemeDataStore.DEFAULT_DYNAMIC_COLOR_SCHEME
    )
    ApplicationTheme(
        darkTheme = currentThemeMode.toComposableBoolean(isSystemInDarkTheme()),
        dynamicColor = currentDynamicColor
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            }
        ) { innerPaddings: PaddingValues ->
//            WriterScreen()
            //SettingScreens()

//            PreviewScreen()
           // OnboardingScreen(scaffoldModifier = Modifier.padding(innerPaddings))
           // return@Scaffold
            ComposeNavigation(
                scaffoldModifier = Modifier.padding(innerPaddings)
            )
        }
    }
}

@Composable
private fun GlobalSideEffects(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope = rememberCoroutineScope(),
) {

    ObserveAsEvents(
        flow = SnackbarController.events,
    ) { snackbarEvent ->
        // launching another scope inside launched effect for ui of snackbar

        scope.launch {
            if (snackbarEvent.dismissPrevious && snackbarHostState.currentSnackbarData != null) {
                snackbarHostState.currentSnackbarData?.dismiss()
                delay(100)
            }

            if (snackbarEvent.message.isEmpty())
                return@launch

            val result = snackbarHostState.showSnackbar(
                message = snackbarEvent.message,
                actionLabel = snackbarEvent.action?.name,
                duration = SnackbarDuration.Short
            )
            when (result) {
                Dismissed -> Unit
                ActionPerformed -> {
                    snackbarEvent.action?.action?.invoke()
                }
            }
        }
    }
}





















