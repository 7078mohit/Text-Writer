package com.storygenerator.core.datastore.di

import com.storygenerator.core.datastore.common.CommonDataStore
import com.storygenerator.core.datastore.onboarding.OnboardingDataStore
import com.storygenerator.core.datastore.theme.ThemeDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dataStoreModule = module {
    singleOf(::ThemeDataStore)
    singleOf(::OnboardingDataStore)
//    singleOf(::PurchasesDataStore)
    singleOf(::CommonDataStore)
}