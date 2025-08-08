package com.kmpstarter.features.text_writer.data.data_source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.kmpstarter.features.text_writer.data.data_source.room.entities.WriterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WriterDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertWriterItem(writerEntity: WriterEntity) : Long

    @Query("SELECT * FROM generated_text")
    fun getWriterItems() : Flow<List<WriterEntity>>
}