package com.kmpstarter.features.text_writer.data.data_source.room.services

import com.kmpstarter.core.db.KmpStarterDatabase
import com.kmpstarter.features.text_writer.data.data_source.room.entities.WriterEntity
import com.kmpstarter.features.text_writer.domain.models.WriterItem

class RoomServices(private val database: KmpStarterDatabase) {


    private val writerDao = database.writerDao()


    fun getWriterItems() = writerDao.getWriterItems()


    suspend fun insertWriterItem(writerEntity: WriterEntity) : Long = writerDao.insertWriterItem(writerEntity = writerEntity)


}

