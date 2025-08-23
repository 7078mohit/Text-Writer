package com.storygenerator.core.di

import com.storygenerator.features.text_writer.di.textWriterModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

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