package com.hitenderpannu.feature.news.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.feature.news.domain.NewsInteractor
import com.hitenderpannu.feature.news.domain.NewsInteractorImpl
import com.hitenderpannu.feature.news.remote.NewsApi
import com.hitenderpannu.feature.news.remote.NewsRemoteRepo
import com.hitenderpannu.feature.news.remote.NewsRemoteRepoImpl
import com.hitenderpannu.feature.news.ui.NewsListFragment
import com.hitenderpannu.feature.news.ui.NewsListFragmentViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NewsListModule(private val fragment: NewsListFragment) {

    @Provides
    @NewsFragmentScope
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @NewsFragmentScope
    fun provideNewsRemoteRepo(newsApi: NewsApi): NewsRemoteRepo {
        return NewsRemoteRepoImpl(newsApi)
    }

    @Provides
    @NewsFragmentScope
    fun provideNewsInteractor(newsRemoteRepo: NewsRemoteRepo): NewsInteractor {
        return NewsInteractorImpl(newsRemoteRepo)
    }

    @NewsFragmentScope
    @Provides
    fun provideViewModelFactory(newsInteractor: NewsInteractor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                when {
                    modelClass.isAssignableFrom(NewsListFragmentViewModel::class.java) -> {
                        return NewsListFragmentViewModel(newsInteractor) as T
                    }
                    else -> {
                        throw Exception("Invalid ViewModel Type")
                    }
                }
            }
        }
    }
}
