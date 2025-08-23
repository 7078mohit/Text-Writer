package com.storygenerator.core.ui.modifiers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.storygenerator.core.ui.composition_locals.LocalTopAppBarScrollBehavior

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Modifier.topAppBarScrollBehavior(): Modifier {
    val scrollBehavior = LocalTopAppBarScrollBehavior.current
    return if (scrollBehavior != null)
        this.nestedScroll(
            connection = scrollBehavior.nestedScrollConnection
        )
    else this
}