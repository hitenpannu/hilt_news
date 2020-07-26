package com.hitenderpannu.feature.news.ui.di

import com.hitenderpannu.base.CoreComponentProvider
import com.hitenderpannu.feature.news.ui.NewsListFragment

internal object DaggerComponentManager {

    fun inject(fragment: NewsListFragment) {
        val applicationContext = fragment.requireContext().applicationContext
        require(applicationContext is CoreComponentProvider) { "Application must provide Core Component" }
        DaggerNewsListComponent.builder()
            .coreComponent(applicationContext.provideCoreComponent())
            .newsListModule(NewsListModule(fragment))
            .build()
            .inject(fragment)
    }
}
