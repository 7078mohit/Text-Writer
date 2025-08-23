package com.storygenerator.features.text_writer.di

import com.storygenerator.features.text_writer.data.data_source.room.services.RoomServices
import com.storygenerator.features.text_writer.data.repository.WriterRepositoryImpl
import com.storygenerator.features.text_writer.domain.repository.WriterRepository
import com.storygenerator.features.text_writer.presentation.viewmodels.WriterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val textWriterModule  = module {
    singleOf(::WriterRepositoryImpl).bind<WriterRepository>()
    singleOf(::RoomServices)
    singleOf(::WriterViewModel)
}