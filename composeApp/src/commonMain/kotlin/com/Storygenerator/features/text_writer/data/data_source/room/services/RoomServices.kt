package com.storygenerator.features.text_writer.data.data_source.room.services

import com.storygenerator.core.db.KmpStarterDatabase
import com.storygenerator.features.text_writer.data.data_source.room.entities.WriterEntity

class RoomServices(private val database: KmpStarterDatabase) {


    private val writerDao = database.writerDao()


    fun getWriterItems() = writerDao.getWriterItems()


    suspend fun insertWriterItem(writerEntity: WriterEntity) : Long = writerDao.insertWriterItem(writerEntity = writerEntity)


}

