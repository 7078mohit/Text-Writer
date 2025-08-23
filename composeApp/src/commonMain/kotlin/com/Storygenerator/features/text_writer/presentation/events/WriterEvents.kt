package com.storygenerator.features.text_writer.presentation.events

sealed class WriterEvents {

    data object GenerateText : WriterEvents()
}