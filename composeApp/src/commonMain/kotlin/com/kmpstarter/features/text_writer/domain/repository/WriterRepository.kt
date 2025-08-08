package com.kmpstarter.features.text_writer.domain.repository

import com.kmpstarter.features.text_writer.domain.models.WriterItem
import kotlinx.coroutines.flow.Flow

interface WriterRepository {

    @Throws(Exception::class)
   suspend fun generateText(prompt : String) : WriterItem

    suspend fun getHistory() : Flow<List<WriterItem>>

    suspend fun insertHistory(writerItem: WriterItem) : Result<WriterItem>

}