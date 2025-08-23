package com.storygenerator.core.db.di

import com.storygenerator.core.db.KmpStarterDatabase
import com.storygenerator.core.db.getKmpDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDatabaseModule: Module

val databaseModule = module {
    includes(platformDatabaseModule)
    single<KmpStarterDatabase> {
        getKmpDatabase(
            databaseProvider = get()
        )
    }
}

