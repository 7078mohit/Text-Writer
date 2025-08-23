package com.storygenerator.core.utils.platform

import android.os.Build

actual val isDynamicColorSupported: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S