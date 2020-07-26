package com.hitenderpannu.feature.news.ui.di

import com.hitenderpannu.base.CoreComponent
import com.hitenderpannu.feature.news.ui.NewsListFragment
import dagger.Component

@NewsFragmentScope
@Component(
    modules = [
        NewsListModule::class
    ],
    dependencies = [
        CoreComponent::class
    ]
)
interface NewsListComponent {

    fun inject(fragment: NewsListFragment)
}
