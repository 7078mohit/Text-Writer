package com.storygenerator.core.utils.di

import com.storygenerator.core.utils.datastore.AppDataStore
import com.storygenerator.core.utils.intents.IntentUtils
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformUtilsModule = module {
    singleOf(::AppDataStore)
    singleOf(::IntentUtils)
}