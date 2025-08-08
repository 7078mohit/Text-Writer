package com.kmpstarter.features.text_writer.data.data_source.dtos

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>,
    val modelVersion: String,
    val responseId: String,
    val usageMetadata: UsageMetadata
)