package com.kmpstarter.features.text_writer.presentation.events

sealed class WriterEvents {

    data object GenerateText : WriterEvents()
}