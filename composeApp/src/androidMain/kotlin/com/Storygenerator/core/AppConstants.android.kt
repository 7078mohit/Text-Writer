package com.storygenerator.core

import com.storygenerator.BuildConfig

actual val APPSTORE_URL =
    "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"