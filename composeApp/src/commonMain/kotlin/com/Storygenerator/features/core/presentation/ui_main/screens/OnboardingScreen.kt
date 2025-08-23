package com.storygenerator.features.core.presentation.ui_main.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.storygenerator.core.datastore.onboarding.OnboardingDataStore
import com.storygenerator.core.events.navigator.interfaces.Navigator
import com.storygenerator.features.core.presentation.ui_main.nav_graph.CoreScreens
import com.storygenerator.features.text_writer.presentation.ui_main.nav_graphs.WriterScreens
import com.storygenerator.theme.Dimens
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

private data class OnboardingSlide(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val feature: List<OnboardingFeatures>
)

private data class OnboardingFeatures(
    val emoji : String,
    val text : String
)

private val onBoardingSlidenotinUse = listOf(
    OnboardingSlide(
        icon = Icons.Default.Psychology,
        title = "AI-Powered Text Generation",
        description = "Create high-quality content with advanced AI technology that understands context and generates enganging text.",
        feature = listOf(
            OnboardingFeatures(emoji =  "", text = "Fast & Efficient" ),
            OnboardingFeatures(emoji =  "", text = "Context-Aware" ),
            OnboardingFeatures(emoji =  "", text = "High Quality Output" )
    )),
    OnboardingSlide(
        icon = Icons.Default.Create,
        title = "Multiple Content Types",
        description = "Generate various types of content including stories,",
        feature = listOf(
            OnboardingFeatures(emoji =  "", text = "Stories & Articles" ),
            OnboardingFeatures(emoji =  "", text = "Emails & Letters" ),
            OnboardingFeatures(emoji =  "", text = "Social Media Posts" )
        )
    ),
    OnboardingSlide(
        icon = Icons.Default.SmartToy,
        title = "Smart Customization",
        description = "Customize your content with tone, style, and length pre",
        feature = listOf(
            OnboardingFeatures(emoji =  "", text = "Style Customization" ),
            OnboardingFeatures(emoji =  "", text = "Length Control" ),
            OnboardingFeatures(emoji =  "", text = "Tone Selection" )
        )

    ),
    OnboardingSlide(
        icon = Icons.Default.AutoAwesome,
        title = "Ready to Create?",
        description = "Start generating amazing content with AI. Your creative",
        feature = listOf(
            OnboardingFeatures(emoji =  "", text = "Get Started" ),
            OnboardingFeatures(emoji =  "", text = "Unlimited Ideas" ),
            OnboardingFeatures(emoji =  "", text = "Professional Results" )
        )

    )
)   // ye use nahi kkrre hai
private val onBoardingSlide = listOf(
    OnboardingSlide(
        icon = Icons.Default.Psychology,
        title = "AI-Powered Text Generation",
        description = "Craft engaging stories, scripts, and articles instantly with our Gemini-powered AI that understands context, emotions, and flow for truly human-like writing.",
        feature = listOf(
            OnboardingFeatures(emoji = "⚡", text = "Fast & Efficient"),
            OnboardingFeatures(emoji = "🧠", text = "Context-Aware"),
            OnboardingFeatures(emoji = "✨", text = "High Quality Output")
        )
    ),
    OnboardingSlide(
        icon = Icons.Default.Create,
        title = "Multiple Content Types",
        description = "Generate captivating stories, social media posts, emails, poems, and more — all tailored to your needs in seconds.",
        feature = listOf(
            OnboardingFeatures(emoji = "📖", text = "Stories & Articles"),
            OnboardingFeatures(emoji = "✉️", text = "Emails & Letters"),
            OnboardingFeatures(emoji = "📱", text = "Social Media Posts")
        )
    ),
    OnboardingSlide(
        icon = Icons.Default.SmartToy,
        title = "Smart Customization",
        description = "Adjust tone, style, and length to match your vision — from casual fun to professional writing, all with a single tap.",
        feature = listOf(
            OnboardingFeatures(emoji = "🎨", text = "Style Customization"),
            OnboardingFeatures(emoji = "📏", text = "Length Control"),
            OnboardingFeatures(emoji = "🎭", text = "Tone Selection")
        )
    ),
    OnboardingSlide(
        icon = Icons.Default.AutoAwesome,
        title = "Ready to Create?",
        description = "Start bringing your ideas to life with AI. Unlock endless creativity and professional results, right at your fingertips.",
        feature = listOf(
            OnboardingFeatures(emoji = "🚀", text = "Get Started"),
            OnboardingFeatures(emoji = "💡", text = "Unlimited Ideas"),
            OnboardingFeatures(emoji = "🏆", text = "Professional Results")
        )
    )
)


@Composable
fun OnboardingScreen(scaffoldModifier : Modifier = Modifier,
                     onboardingDataStore: OnboardingDataStore = koinInject(),
                     navigator : Navigator = koinInject()){

    val scope = rememberCoroutineScope()

    OnboardingScreenContent(modifier = scaffoldModifier ,
        onGetStarterClick = {
            scope.launch {
                onboardingDataStore.setIsOnboarded(true)
                navigator.navigateTo(
                    WriterScreens.Root,
                    popUpTo = CoreScreens.OnBoarding,
                    inclusive = true
                )
            }
     })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OnboardingScreenContent(modifier : Modifier , onGetStarterClick:() -> Unit){

    val pagerState = rememberPagerState(initialPage =   0 , pageCount = { onBoardingSlide.size } )

    val isLastPage by derivedStateOf {
        pagerState.currentPage == onBoardingSlide.lastIndex
    }

    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = pagerState.currentPage != 0
    ) {
       scope.launch {
           pagerState.animateScrollToPage(
               page = pagerState.currentPage - 1
           )
       }
    }

    println(onBoardingSlide.size)
    println(onBoardingSlide.lastIndex)


    Surface {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OnBoardingIndicators(
                modifier = Modifier,
                totalPages = onBoardingSlide.lastIndex,
                currentpage = pagerState.currentPage
            )

            HorizontalPager(
                modifier = Modifier.weight(1f),
                state = pagerState,
            ){ pagerIndex ->

                OnBoardingSlideUi(
                    slide = onBoardingSlide[pagerIndex]
                )

            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(
                    Dimens.paddingLarge
                ).height(Dimens.buttonHeight),
                shape = RoundedCornerShape(Dimens.buttonRadius),
                onClick = {
                if (isLastPage)
                {
                    onGetStarterClick.invoke()
                    return@Button
                }
                scope.launch {
                    pagerState.animateScrollToPage(
                        page = pagerState.currentPage + 1
                    )
                }
            }
            ){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isLastPage){
                            "Get Started"
                        }
                        else{
                            "Next"
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(Dimens.paddingSmall))

                    Icon(
                        imageVector = if (isLastPage) Icons.Default.CheckCircle else Icons.Default.ArrowForward,
                        contentDescription = null
                    )

                }
            }

        }
    }

}

@Composable
fun OnBoardingIndicators(modifier: Modifier.Companion, totalPages: Int, currentpage: Int) {
    Spacer(modifier = Modifier.height(Dimens.paddingMedium))
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(times =  totalPages + 1 ){ index ->

            val isActive = index == currentpage

            Box(
                modifier = Modifier.size(8.dp).clip(CircleShape)
                .background(
                    if (isActive){
                        MaterialTheme.colorScheme.primary
                    }
                    else{
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f)
                    }
                )
            )

            if (index < totalPages){
                Spacer(modifier = Modifier.height(Dimens.paddingSmall))
            }
        }
    }
}

@Composable
private fun OnBoardingSlideUi(slide: OnboardingSlide) {
    Column (modifier = Modifier.fillMaxWidth()
        .padding(Dimens.paddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        ){

        Image(
            modifier = Modifier.size(100.dp)
                .clip(RoundedCornerShape(Dimens.paddingMedium))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(Dimens.paddingMedium),
            imageVector = slide.icon,
            contentDescription = slide.title,
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        Text(
            text = slide.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 24.sp
            ),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        Text(
            text = slide.description,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimens.paddingLarge))

        if (slide.feature.isEmpty()) return

        slide.feature.forEach { features ->
            AssistChip(
                shape = RoundedCornerShape(Dimens.paddingLarge),
                onClick = {},
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {

                        Text(
                            text = features.emoji,
                            fontSize = 18.sp
                        )

                        Text(
                            text = features.text,
                            style = MaterialTheme.typography.labelLarge
                        )

                    }
                }
            )

        }

    }
}