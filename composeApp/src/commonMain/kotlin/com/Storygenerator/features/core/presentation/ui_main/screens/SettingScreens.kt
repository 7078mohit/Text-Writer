package com.storygenerator.features.core.presentation.ui_main.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.storygenerator.core.AppConstants
import com.storygenerator.core.datastore.theme.ThemeDataStore
import com.storygenerator.core.events.enums.ThemeMode
import com.storygenerator.core.ui.components.material_cupertino.dropdown.CupertinoDropdownItem
import com.storygenerator.core.ui.components.material_cupertino.sections.CupertinoSection
import com.storygenerator.core.ui.components.material_cupertino.sections.CupertinoSectionRow
import com.storygenerator.core.ui.layouts.lists.ScrollableColumn
import com.storygenerator.core.utils.intents.IntentUtils
import com.storygenerator.core.utils.platform.isDynamicColorSupported
import com.storygenerator.theme.Dimens
import kmpstarter.composeapp.generated.resources.Res
import kmpstarter.composeapp.generated.resources.setting_title
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun SettingScreens(
    themeDataStore: ThemeDataStore = koinInject(),
    intentUtils: IntentUtils = koinInject()
) {
    val currentThemeMode by themeDataStore.themeMode.collectAsState(
        initial = ThemeDataStore.DEFAULT_THEME_MODE
    )

    val isDynamicColorEnabled by themeDataStore.dynamicColor.collectAsState(
        initial = ThemeDataStore.DEFAULT_DYNAMIC_COLOR_SCHEME
    )

    SettingScreenContent(
        themeMode = currentThemeMode,
        onThemeModeChange = themeDataStore::setThemeMode,
        isDynamicColorEnabled = isDynamicColorEnabled,
        onDynamicColorChange = themeDataStore::setDynamicColor,

        //App support
        onContactUsClick={
            intentUtils.sendEmail(
                email = AppConstants.CONTACT_EMAIL,
                subject = "",
                body = ""
            )
        },
        onRateAppClick ={
            intentUtils.openAppStore()
        },
        onShareAppClick={
            intentUtils.shareText(
                text = AppConstants.SHARE_APP_TEXT                       //url diya h
            )
        },
        onPrivacyPolicyClick ={
            intentUtils.openUrl(
                url = AppConstants.PRIVACY_POLICY_URL
            )
        }

    )

}

@OptIn(ExperimentalMaterial3Api::class, InternalResourceApi::class)
@Composable
fun SettingScreenContent(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    isDynamicColorEnabled : Boolean ,
    onDynamicColorChange : (Boolean) -> Unit,

    //App support
    onContactUsClick:() -> Unit,
    onRateAppClick:() -> Unit,
    onShareAppClick:()-> Unit,
    onPrivacyPolicyClick:() -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text =
                            stringResource(
                                resource = Res.string.setting_title
                            )
                    )
                }
            )
        }
    ) { innerpadding: PaddingValues ->

        ScrollableColumn(
            modifier = Modifier.fillMaxSize().padding(innerpadding).padding(Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {

            // theme section
            CupertinoSection(
                title = "Theme"
            ) {
                var isExpanded by rememberSaveable { mutableStateOf(false) }

                CupertinoSectionRow(
                    label = "Theme",
                    value = getThemeModeLabel(themeMode),                         // isme ata h selected dropdownitem
                    icon = Icons.Default.Palette,
                    isExpanded = isExpanded,
                    onExpandedChange = {
                        isExpanded = it
                    },
                    content = {
                        // options
                        ThemeMode.entries.forEachIndexed { index, mode ->

                            CupertinoDropdownItem(
                                text = getThemeModeLabel(mode),
                                leadingIconTint = MaterialTheme.colorScheme.primary,
                                leadingIcon = when (mode) {
                                    ThemeMode.LIGHT -> Icons.Default.Light
                                    ThemeMode.DARK -> Icons.Default.DarkMode
                                    ThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
                                },
                                showDivider = index != ThemeMode.entries.lastIndex,
                                onClick = {
                                    isExpanded = false
                                    onThemeModeChange(mode)
                                }
                            )
                        }

                    },
                    isLast = !isDynamicColorSupported
                )
            }

                    // dynamic color
                if (isDynamicColorSupported){
                    CupertinoSectionRow(
                        label = "Dynamic Color",
                        isSwitchChecked = isDynamicColorEnabled,
                        onSwitchChange = onDynamicColorChange,
                        icon = Icons.Default.DynamicFeed,
                        isLast = true
                    )
                }

                CupertinoSection(
                    title = "App Support"
                ){
                    CupertinoSectionRow(
                        label = "Contact Us",
                        icon = Icons.AutoMirrored.Filled.ContactSupport,
                        onClick = onContactUsClick
                    )
                    CupertinoSectionRow(
                        label = "Rate App",
                        icon = Icons.Default.Star,
                        onClick = onRateAppClick
                    )
                    CupertinoSectionRow(
                        label = "Share App",
                        icon = Icons.Default.Share,
                        onClick = onShareAppClick
                    )
                }


                CupertinoSection(
                    title = "Legal"
                ){
                    CupertinoSectionRow(
                        label = "Privacy Policy",
                        icon = Icons.Default.Policy,
                        onClick = onPrivacyPolicyClick
                    )
                }



        }


    }

}


private fun getThemeModeLabel(themeMode: ThemeMode): String = when (themeMode) {
    ThemeMode.LIGHT -> "Light Mode"
    ThemeMode.DARK -> "Dark Mode"
    ThemeMode.SYSTEM -> "System "
}