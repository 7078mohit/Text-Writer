package com.kmpstarter.features.text_writer.di

import com.kmpstarter.features.text_writer.data.data_source.room.services.RoomServices
import com.kmpstarter.features.text_writer.data.repository.WriterRepositoryImpl
import com.kmpstarter.features.text_writer.domain.repository.WriterRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val textWriterModule  = module {
    singleOf(::WriterRepositoryImpl).bind<WriterRepository>()
    singleOf(::RoomServices)
}