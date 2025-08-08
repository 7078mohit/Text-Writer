package com.kmpstarter.features.text_writer.data.data_source.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UsageMetadata(
    val candidatesTokenCount: Int,
    val promptTokenCount: Int,
    val promptTokensDetails: List<PromptTokensDetail>,
    val thoughtsTokenCount: Int,
    val totalTokenCount: Int
)