package com.storygenerator

import androidx.compose.ui.window.ComposeUIViewController
import com.storygenerator.core.di.initKoin
import com.storygenerator.core.purchases.initRevenueCat

fun mainViewController() = ComposeUIViewController(
    configure = {
        //AuthUtils.initGoogleAuthProvider()
        initKoin()
        initRevenueCat()
    }
) {
    App()
}