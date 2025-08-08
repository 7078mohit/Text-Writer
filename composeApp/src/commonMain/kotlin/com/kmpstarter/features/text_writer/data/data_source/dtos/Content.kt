package com.kmpstarter.features.text_writer.data.data_source.dtos

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String
)