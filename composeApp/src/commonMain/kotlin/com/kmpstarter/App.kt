package com.kmpstarter

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.kmpstarter.core.datastore.theme.ThemeDataStore
import com.kmpstarter.core.events.controllers.SnackbarController
import com.kmpstarter.core.events.utils.ObserveAsEvents
import com.kmpstarter.core.navigation.ComposeNavigation
import com.kmpstarter.core.utils.logging.Log
import com.kmpstarter.features.text_writer.domain.repository.WriterRepository
import com.kmpstarter.theme.ApplicationTheme
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

    LaunchedEffect(Unit){
        val TAG = "WRITER_REPOSITORY"
      val writerItem =   writerRepository.generateText( prompt = "please text about my life my name is mohit from rishikesh")

        Log.d(TAG , writerItem)
        delay(1000L)

        val result = writerRepository.insertHistory(writerItem = writerItem).onSuccess {
            Log.d(TAG , "INSERTED SUCCESSFULLY")
            writerRepository.getHistory().collect{
                Log.d(
                    TAG,
                    it
                )
            }
        }.onFailure{
            Log.d(TAG , "INSERTION ERROR")
        }



    }



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





















