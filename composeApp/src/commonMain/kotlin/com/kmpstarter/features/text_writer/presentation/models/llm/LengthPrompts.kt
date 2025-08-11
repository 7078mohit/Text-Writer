package com.kmpstarter.features.text_writer.presentation.models.llm

import kotlinx.serialization.Serializable

@Serializable
data class LengthPrompts(
    val long: String,
    val medium: String,
    val short: String
) {
    fun getPrompt(length: String) = when (length.lowercase()) {
        "short" -> short
        "medium" -> medium
        "long" -> long

        else -> throw IllegalStateException("Invalid length:$length")
    }
}