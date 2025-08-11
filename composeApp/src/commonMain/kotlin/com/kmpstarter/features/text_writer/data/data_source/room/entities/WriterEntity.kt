package com.kmpstarter.features.text_writer.data.data_source.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kmpstarter.features.text_writer.domain.models.WriterItem

@Entity(
    tableName = "generated_text"
)
data class WriterEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val content : String
){

fun toWriterItem(): WriterItem{                             // is fun se ab hum room se data get krke sam gemini api ka response dikha denge
    return WriterItem(
        title = title,
        content = content
    )
  }
}