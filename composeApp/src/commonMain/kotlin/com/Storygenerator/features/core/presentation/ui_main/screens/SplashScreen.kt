package com.storygenerator.features.core.presentation.ui_main.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.storygenerator.core.datastore.onboarding.OnboardingDataStore
import com.storygenerator.core.events.navigator.interfaces.Navigator
import com.storygenerator.features.text_writer.presentation.ui_main.nav_graphs.WriterScreens
import com.storygenerator.theme.Dimens
import com.storygenerator.features.core.presentation.ui_main.nav_graph.CoreScreens
import kmpstarter.composeapp.generated.resources.Res
import kmpstarter.composeapp.generated.resources.app_description
import kmpstarter.composeapp.generated.resources.app_name
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    onboardingDataStore: OnboardingDataStore = koinInject(),
    navigator : Navigator = koinInject()
){

    LaunchedEffect(Unit){
        delay(2500)
        val route = if (onboardingDataStore.isOnboarded.first()) WriterScreens.Root else CoreScreens.OnBoarding

        navigator.navigateTo(
            route = route,
            popUpTo = CoreScreens.Splash,
            inclusive = true
        )
    }



    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            )
        )
    )

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier.size(100.dp)
                    .clip(RoundedCornerShape(Dimens.paddingMedium))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(Dimens.paddingMedium),
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onPrimary
                )
            )

            Spacer(modifier = Modifier.height(Dimens.paddingExtraLarge))

            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(Dimens.paddingSmall))

            Text(
                text = stringResource(Res.string.app_description),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Dimens.paddingExtraLarge))

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = Dimens.paddingLarge),
                progress = { progress }
            )
            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            Text(
                text = "Loading...",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}