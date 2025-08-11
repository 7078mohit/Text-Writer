package com.kmpstarter.features.text_writer.presentation.ui_main.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmpstarter.core.ui.components.buttons.GenerateAiButton
import com.kmpstarter.core.ui.components.text.ErrorText
import com.kmpstarter.core.ui.layouts.lists.ScrollableColumn
import com.kmpstarter.features.text_writer.presentation.models.llm.Controller
import com.kmpstarter.features.text_writer.presentation.ui_main.layouts.ControllerLayout
import com.kmpstarter.features.text_writer.presentation.viewmodels.WriterViewModel
import com.kmpstarter.theme.Dimens
import kmpstarter.composeapp.generated.resources.Res
import kmpstarter.composeapp.generated.resources.app_name
import kmpstarter.composeapp.generated.resources.writer_prompt_placeholder
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun WriterScreen(modifier: Modifier = Modifier , viewModel: WriterViewModel = koinInject()) {


    val state by viewModel.state.collectAsStateWithLifecycle()

    WriterScreenContent(
        prompt = state.prompt,
        promptError = state.promptErrorr,
        promptLength = state.promptLength,
        promptMaxLength = state.promptMaxLength,
        onPromptChange = {
            viewModel.onPromptChange(
                value = it
            )
        },
        selectedLength = state.selectedLength,
        //dummy hai abhi
        onLengthSelected = {
            viewModel.onLengthChange(it)
        },
        onLockedItemClicked = {},
        isItemLocked = false,
        controllers = state.llmConfig?.controllers,
        selectedControllerMap = state.selectedControllersMap,
        onControllerSelected = viewModel::onControllerSelected,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriterScreenContent(

    modifier: Modifier = Modifier,
    isItemLocked : Boolean,


    prompt: String,
    promptError: String,
    promptLength: Int,
    promptMaxLength: Int,
    onPromptChange: (String) -> Unit,

    // Length parameters
    selectedLength : String,
    onLengthSelected:(String) -> Unit,
    onLockedItemClicked:()->Unit,

    // controllers layout
    controllers : List<Controller>?,
    selectedControllerMap : Map<String , String>,
    onControllerSelected : (String , String) -> Unit,


    ) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            resource = Res.string.app_name
                        )
                    )
                }
            )
        }

    ) { innnerpadding: PaddingValues ->

        Box(modifier = Modifier.fillMaxSize().padding(innnerpadding)) {

            ScrollableColumn(modifier = Modifier.fillMaxSize().padding(
                Dimens.paddingMedium
            )) {

                //prompt Section
                PromptSection(
                    prompt = prompt,
                    error = promptError,
                    length = promptLength,
                    maxLength = promptMaxLength,
                    onPromptChange = onPromptChange
                )

                Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                LengthSelector(
                    selectedLength = selectedLength,
                    onLengthSelected = onLengthSelected,
                    onLockedItemClicked = onLockedItemClicked,
                    isItemLocked = isItemLocked
                )

                Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                if (controllers != null) {
                    ControllerLayout(
                        controllers = controllers,
                        onControllerSelected = onControllerSelected,
                        selectedControllersMap = selectedControllerMap,
                        onLockedItemClicked = onLockedItemClicked,
                        isItemsLocked = isItemLocked
                    )
                }

                Spacer(modifier = Modifier.height(200.dp))


            }


            GenerateAiButton(
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                    horizontal = Dimens.paddingSmall),
                isGenerating = false,
                onClick = {}
            )
        }

    }
}

@Composable
fun LengthSelector(
    modifier: Modifier = Modifier,
    selectedLength: String,
    onLengthSelected: (String) -> Unit,
    onLockedItemClicked: () -> Unit,
    isItemLocked: Boolean
){
    val lockedLengths = remember {
        listOf(
            "medium",
            "long"
        )
    }
    val lengthOptions = remember {
        listOf(
            "short",
            "medium",
            "long"
        )
    }

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        lengthOptions.forEachIndexed { index, option ->

            val isLocked = option in lockedLengths && isItemLocked

            val isSelected = option == selectedLength
            SegmentedButton(
                selected = isSelected,

                onClick = {
                    if(isLocked){
                        onLockedItemClicked()
                    }
                    else{
                        onLengthSelected(option)
                    }
                },
                shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                count = lengthOptions.size
                )
            ){
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    if (isLocked){
                        Icon(Icons.Default.Lock , contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary)
                    }

                    Text(
                        text = option.lowercase().replaceFirstChar { it.uppercase() }
                    )
                }
            }
        }
    }
}

@Composable
fun PromptSection(
    modifier: Modifier = Modifier,
    prompt: String,
    error: String,
    length: Int,
    maxLength: Int,
    onPromptChange: (String) -> Unit
) {
    Card(
        modifier= modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder(enabled = true),
    ) {

        TextField(
            modifier = modifier.fillMaxWidth().heightIn(min = 200.dp),
            value = prompt,
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            onValueChange = {
                onPromptChange(it)
            },
            placeholder = {
                Text(text =
                    stringResource(
                        Res.string.writer_prompt_placeholder
                    ))
            },
            supportingText = {
                Box(
                    modifier = Modifier.fillMaxSize().padding(Dimens.paddingSmall),
                    contentAlignment = Alignment.BottomEnd
                ){
                    if (error.isNotEmpty()){
                        ErrorText(error = error , textAlign = TextAlign.End)
                    }
                    else{
                        Text(
//                            textAlign = TextAlign.End,
                            text = "$length/$maxLength" ,
                            color = if (length > maxLength) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                }
            }

        )


    }

}
