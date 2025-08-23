package com.storygenerator.core.events.di

import com.storygenerator.core.datastore.onboarding.OnboardingDataStore
import com.storygenerator.core.datastore.theme.ThemeDataStore
import com.storygenerator.core.events.navigator.DefaultNavigator
import com.storygenerator.core.events.navigator.interfaces.Navigator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val eventsModule = module {
    singleOf(::ThemeDataStore)
    singleOf(::OnboardingDataStore)
    singleOf(::DefaultNavigator).bind<Navigator>()
}