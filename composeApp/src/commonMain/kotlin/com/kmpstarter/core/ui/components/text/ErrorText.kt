package com.kmpstarter.core.ui.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorText(error: String , textAlign: TextAlign = TextAlign.Center) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = error,
        textAlign =textAlign,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.error
    )
}