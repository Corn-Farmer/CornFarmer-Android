package org.cornfarmer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.cornfarmer.util.CFDebugTree
import timber.log.Timber

@HiltAndroidApp
class CornFarmerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(CFDebugTree())
        }
    }
}
