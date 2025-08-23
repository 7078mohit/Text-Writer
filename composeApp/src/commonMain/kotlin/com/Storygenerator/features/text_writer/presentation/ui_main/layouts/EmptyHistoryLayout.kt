package com.storygenerator.features.text_writer.presentation.ui_main.layouts


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.storygenerator.theme.Dimens
import kmpstarter.composeapp.generated.resources.Res
import kmpstarter.composeapp.generated.resources.writer_empty_history
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyHistoryLayout(modifier : Modifier = Modifier) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Icon(
            Icons.Default.History,
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(Dimens.paddingSmall))

        Text(
            text = stringResource(
                resource = Res.string.writer_empty_history
            ),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

    }
}