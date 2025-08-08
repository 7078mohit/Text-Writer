package com.kmpstarter.core.di

import com.kmpstarter.features.text_writer.di.textWriterModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreModule,
//            authModule,
            /*Todo add modules here*/
            textWriterModule
        )
    }
}