package com.storygenerator.features.text_writer.presentation.models.llm

import kotlinx.serialization.Serializable

@Serializable
data class Controller(
    val keys: List<String>,
    val lockedPromptsKeys: List<String>,
    val promptKey: String,
    val prompts: Map<String,String>,
    val required: Boolean,
    val title: String
)