package com.kmpstarter.features.text_writer.data.data_source.dtos

import kotlinx.serialization.Serializable

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String,
    val index: Int
)