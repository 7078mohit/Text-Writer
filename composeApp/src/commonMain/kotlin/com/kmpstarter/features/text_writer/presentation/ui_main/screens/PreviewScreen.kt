package com.kmpstarter.features.text_writer.presentation.ui_main.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmpstarter.core.events.controllers.SnackbarController
import com.kmpstarter.core.ui.layouts.lists.ScrollableColumn
import com.kmpstarter.core.utils.intents.IntentUtils
import com.kmpstarter.features.text_writer.domain.models.WriterItem
import com.kmpstarter.features.text_writer.presentation.viewmodels.WriterViewModel
import com.kmpstarter.theme.Dimens
import com.kmpstarter.theme.onErrorLight
import kmpstarter.composeapp.generated.resources.Res
import kmpstarter.composeapp.generated.resources.history_name
import kmpstarter.composeapp.generated.resources.preview_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun PreviewScreen(
   viewModel: WriterViewModel = koinInject(),
   intentUtils: IntentUtils  = koinInject()
){
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()

    PreviewScreenContent(
       writerItem = state.writerItem,
        onNavigateUp = {},
        onCopyClick = {
            scope.launch {

                intentUtils.copyToClipboard(
                    text = "${state.writerItem.title} \n ${state.writerItem.content}"
                )
                SnackbarController.sendAlert("Copied!!")
            }
        },
        onShareClick = {
            intentUtils.shareText(
               text =  "${state.writerItem.title} \n ${state.writerItem.content}"
            )},
        onReportClick = {
            scope.launch{
            // todo save report into firebase or using custom api
            SnackbarController.sendAlert("Reported!")
        }}
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreenContent(
    modifier: Modifier = Modifier,
    writerItem: WriterItem,
    onNavigateUp:() -> Unit,
    onShareClick:() -> Unit,
    onCopyClick:() -> Unit,
    onReportClick:() -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text =
                        stringResource(
                            resource = Res.string.preview_title
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {onNavigateUp.invoke()}){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null)
                    }
                }
            )
        }
    ) {   innerPadding : PaddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(Dimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = CardDefaults.outlinedCardBorder()
            ){

                ScrollableColumn(modifier = Modifier.fillMaxSize().padding(Dimens.paddingLarge),
                    verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
                    ){

                    Text(
                        text = writerItem.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = writerItem.content,
                        style = MaterialTheme.typography.bodyMedium,
                    )

                }
            }

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)) {
                FilledTonalButton(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onShareClick()
                    }
                ){

                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Dimens.paddingExtraSmall))

                    Text(
                        text = "Share",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                FilledTonalButton(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onCopyClick()
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Dimens.paddingExtraSmall))

                    Text(
                        text = "Copy",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            TextButton(
                onClick = {onReportClick.invoke()},
                colors = ButtonDefaults.outlinedButtonColors()
            ){
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(Dimens.paddingExtraSmall))

                Text(
                    text = "Report",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

}