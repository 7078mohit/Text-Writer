package com.kmpstarter.features.text_writer.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class WriterItem(
    val title : String = "" ,
    val content : String =""
)