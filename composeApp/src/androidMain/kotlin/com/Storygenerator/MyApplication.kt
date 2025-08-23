package com.storygenerator

import android.app.Application
import com.storygenerator.core.di.initKoin
import com.storygenerator.core.purchases.initRevenueCat
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
  //      AuthUtils.initGoogleAuthProvider()
        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }
        initRevenueCat()
    }

}

