package com.storygenerator.core.purchases.di


import com.storygenerator.core.purchases.presentation.viewmodels.PurchaseDialogViewModel
import com.storygenerator.core.purchases.presentation.viewmodels.PurchaseViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val purchasesModule = module {
    viewModelOf(::PurchaseDialogViewModel)
    singleOf(::PurchaseViewModel)
}