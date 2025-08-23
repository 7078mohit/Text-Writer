package com.storygenerator.features.text_writer.data.data_source.room.mappers

import com.storygenerator.features.text_writer.data.data_source.room.entities.WriterEntity
import com.storygenerator.features.text_writer.domain.models.WriterItem

fun WriterItem.toEntity( )  = WriterEntity(
    title = title,
    content = content
)