package com.hitenderpannu.base

import com.hitenderpannu.network.RetrofitModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class
    ]
)
interface CoreComponent {

    fun inject(application: MainApplication)

    fun provideRetrofit(): Retrofit
}
