package com.kmpstarter.features.text_writer.presentation.models.llm

import kotlinx.serialization.Serializable

@Serializable
data class LlmConfig(
    val controllers: List<Controller>,
    val systemInstructions: SystemInstructions
)