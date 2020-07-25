package com.hitenderpannu.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.e("url:" + retrofit.baseUrl())
    }
}
