package com.storygenerator.features.text_writer.presentation.ui_main.layouts

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.storygenerator.core.ui.components.material_cupertino.dropdown.CupertinoDropdownItem
import com.storygenerator.core.ui.components.material_cupertino.sections.CupertinoSection
import com.storygenerator.core.ui.components.material_cupertino.sections.CupertinoSectionRow
import com.storygenerator.features.text_writer.presentation.models.llm.Controller

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControllerLayout(
    modifier: Modifier = Modifier,
    title : String = "Configuration",
    controllers : List<Controller>,
    selectedControllersMap : Map<String , String>,
    isItemsLocked:Boolean ,
    onControllerSelected :(String, String) -> Unit,
    onLockedItemClicked :() -> Unit
) {

    val focusManager = LocalFocusManager.current

    CupertinoSection(
        title = title,
        content = {

            controllers.forEachIndexed { index, controller ->
                var isExpanded by rememberSaveable{
                    mutableStateOf(false)
                }

                val selectedValue = selectedControllersMap[controller.title] ?: ""                          // ye niche wale onclick ho jane ke bad change krega kyonki usko click krte hi viewmodel ki state wale map me reflect hogi value fir yhhan ayegi
                CupertinoSectionRow(
                    dropdownMenuModifier = Modifier.heightIn(
                        max = 320.dp
                    ),
                    label = controller.title,
                    value = selectedValue.lowercase().replaceFirstChar { it.uppercase() },
                    icon = getControllerIcon(controller.title),
                    isExpanded = isExpanded,
                   onExpandedChange = {
                       isExpanded = it
                   },
                    isLast = index == controllers.lastIndex,
                    onPopupShown = {
                        focusManager.clearFocus()
                    }
                )
     // lambda h ye
                {
                    controller.keys.forEachIndexed { keyIndex, key ->

                        val isLocked = key in controller.lockedPromptsKeys  && isItemsLocked

                        CupertinoDropdownItem(
                           text = key.lowercase().replaceFirstChar { it.uppercase() },
                            onClick = {
                                isExpanded = false
                                if (isLocked){
                                    onLockedItemClicked()
                                }
                                else{
                                    onControllerSelected(
                                        controller.title ,
                                        key
                                    )
                                }
                            },
                            leadingIconTint = if (isLocked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            leadingIcon = if(isLocked) Icons.Default.Lock else getControllerItemIcon(key),
                            showDivider = keyIndex != controller.keys.lastIndex
                        )
                    }

                    }

                }
            }

    )


}

private fun getControllerItemIcon(key: String): ImageVector {

   return when(key.lowercase()) {
        //select genre
        "fantasy" -> Icons.Default.AutoAwesome
        "mystery" -> Icons.Default.Visibility
        "romance" -> Icons.Default.Favorite
        "sci-fi" -> Icons.Default.Science
        "horror" -> Icons.Default.DarkMode
        "adventure" -> Icons.Default.Money
        "drama" -> Icons.Default.Diversity3
        "comedy" -> Icons.Default.EmojiEmotions

        //select style
        "descriptive" -> Icons.Default.Description
        "conversational" -> Icons.Default.Chat
        "poetic" -> Icons.Default.HistoryEdu
        "fast-paced" -> Icons.Default.Speed
        "atmospheric" -> Icons.Default.WbSunny
        "character-driven" -> Icons.Default.Person

        //select tone
        "uplifting" -> Icons.Default.Celebration
        "dark" -> Icons.Default.DarkMode
        "whimsical" -> Icons.Default.Flight
        "suspenseful" -> Icons.Default.Psychology
        "heartwarming" -> Icons.Default.VolunteerActivism
        "thought-provoking" -> Icons.Default.Lightbulb


        //select language
        "english" -> Icons.Default.Translate
        "spanish" -> Icons.Default.Translate
        "french" -> Icons.Default.Translate
        "german" -> Icons.Default.Translate
        "italian" -> Icons.Default.Translate
        "portuguese" -> Icons.Default.Translate
        "russian" -> Icons.Default.Translate
        "japanese" -> Icons.Default.Translate
        "chinese" -> Icons.Default.Translate
        "arabic" -> Icons.Default.Translate
        "hindi" -> Icons.Default.Translate
        "korean" -> Icons.Default.Translate

        else -> Icons.Default.Edit

    }

}

@Composable
private fun getControllerIcon(title: String): ImageVector {
   return when(title.lowercase()){
        "select genre" -> Icons.Default.Movie
        "select style" -> Icons.Default.Palette
        "select tone" -> Icons.Default.Speaker
        "select language" -> Icons.Default.Language
        else -> Icons.Default.Edit
    }


}
