package com.storygenerator.features.text_writer.presentation.models.llm

import kotlinx.serialization.Serializable

@Serializable
data class SystemInstructions(
    val lengthPrompts: LengthPrompts,
    val lockedLengths: List<String>,
    val prompt: String
)
