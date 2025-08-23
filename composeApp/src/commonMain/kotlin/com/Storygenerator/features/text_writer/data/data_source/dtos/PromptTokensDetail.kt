package com.storygenerator.features.text_writer.data.data_source.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PromptTokensDetail(
    val modality: String,
    val tokenCount: Int
)