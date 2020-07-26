package com.hitenderpannu.base

import android.app.Application
import com.hitenderpannu.network.RetrofitModule
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class MainApplication : Application(), CoreComponentProvider {

    @Inject lateinit var retrofit: Retrofit

    private val coreComponent by lazy {
        DaggerCoreComponent.builder()
            .retrofitModule(RetrofitModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        coreComponent.inject(this)
    }

    override fun provideCoreComponent(): CoreComponent {
        return coreComponent
    }
}
