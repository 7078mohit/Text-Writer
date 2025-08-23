package com.storygenerator.core.di

import com.storygenerator.core.datastore.di.dataStoreModule
import com.storygenerator.core.db.di.databaseModule

import com.storygenerator.core.events.di.eventsModule
import com.storygenerator.core.ktor.di.ktorModule
import com.storygenerator.core.purchases.di.purchasesModule
import com.storygenerator.core.utils.di.utilsModule
import org.koin.dsl.module

val coreModule = module {
    includes(
        utilsModule,
        databaseModule,
        eventsModule,
        dataStoreModule,
        purchasesModule,
        ktorModule
    )
}