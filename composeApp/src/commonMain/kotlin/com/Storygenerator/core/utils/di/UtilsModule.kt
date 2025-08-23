package com.storygenerator.core.utils.di

import org.koin.core.module.Module
import org.koin.dsl.module

val commonUtilsModule = module {

}

expect val platformUtilsModule: Module

val utilsModule = module {
    includes(
        commonUtilsModule,
        platformUtilsModule
    )
}