package com.kmpstarter.features.text_writer.presentation.ui_main.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.util.TableInfo
import com.kmpstarter.core.events.navigator.interfaces.Navigator
import com.kmpstarter.core.ui.layouts.lists.CupertinoLazyColumn
import com.kmpstarter.features.text_writer.domain.models.WriterItem
import com.kmpstarter.features.text_writer.presentation.ui_main.layouts.EmptyHistoryLayout
import com.kmpstarter.features.text_writer.presentation.ui_main.nav_graphs.WriterScreens
import com.kmpstarter.features.text_writer.presentation.viewmodels.WriterViewModel
import com.kmpstarter.theme.Dimens
import kmpstarter.composeapp.generated.resources.Res
import kmpstarter.composeapp.generated.resources.history_name
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun HistoryScreen(
    viewModel: WriterViewModel = koinInject(),
    navigator : Navigator = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    HistoryScreenContent(
        modifier = Modifier,
        list = state.writerItemsHistory,
        onHistoryClick = { writerItem ->
            scope.launch {

                viewModel.onWriterItemChange(writerItem)

            navigator.navigateTo(
            route = WriterScreens.Preview
            )
            }}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreenContent(
    modifier: Modifier = Modifier,
    list: List<WriterItem>,
    onHistoryClick:(WriterItem) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text =
                        stringResource(
                            resource = Res.string.history_name
                        )
                    )
                }
            )
        }
    ) {   innerPadding : PaddingValues ->

    if (list.isEmpty()){
        EmptyHistoryLayout(modifier = Modifier.fillMaxSize().padding(innerPadding))
        return@Scaffold
    }
    else{

        CupertinoLazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                horizontal = Dimens.paddingMedium,
                vertical = Dimens.paddingLarge
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
        ) {
            items(list){  item ->
                HistoryCard(writerItem = item ,
                    onHistoryClick = onHistoryClick)
            }
        }

    }
}
}

@Composable
fun HistoryCard(writerItem : WriterItem ,onHistoryClick: (WriterItem) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = {onHistoryClick(writerItem)},
        border = CardDefaults.outlinedCardBorder()
        ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(
                Dimens.paddingLarge
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
        ) {

            Text(
                text = writerItem.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = writerItem.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }


    }

}