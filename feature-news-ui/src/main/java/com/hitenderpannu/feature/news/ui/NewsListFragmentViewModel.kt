package com.hitenderpannu.feature.news.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.feature.news.domain.NewsInteractor
import com.hitenderpannu.feature.news.domain.NewsInteractorState
import com.hitenderpannu.feature.news.domain.NewsInteractorUpdate
import com.hitenderpannu.feature.news.entity.NewsArticle

class NewsListFragmentViewModel(private val newsInteractor: NewsInteractor) : ViewModel() {

    private val mutableNewsList: MutableLiveData<List<NewsArticle>?> = MutableLiveData();
    private val mutableErrorMessage: MutableLiveData<String?> = MutableLiveData();
    private val mutableShouldShowProgress: MutableLiveData<Boolean> = MutableLiveData();
    val newsListLiveData: LiveData<List<NewsArticle>?> = mutableNewsList
    val errorMessageLiveData: LiveData<String?> = mutableErrorMessage
    val shouldShowProgressLiveData: LiveData<Boolean> = mutableShouldShowProgress

    init {
        newsInteractor.observeState().subscribe { updateUi(it) }
    }

    private fun updateUi(state: NewsInteractorState) {
        when (state) {
            NewsInteractorState.Init -> {
                mutableNewsList.postValue(emptyList())
                mutableErrorMessage.postValue(null)
                mutableShouldShowProgress.postValue(false)
            }
            is NewsInteractorState.Loading -> {
                mutableNewsList.postValue(emptyList())
                mutableErrorMessage.postValue(null)
                mutableShouldShowProgress.postValue(true)
            }
            is NewsInteractorState.Success -> {
                mutableNewsList.postValue(state.news)
                mutableErrorMessage.postValue(null)
                mutableShouldShowProgress.postValue(false)
            }
            is NewsInteractorState.Failed.ApiError -> {
                mutableNewsList.postValue(null)
                mutableErrorMessage.postValue(state.message)
                mutableShouldShowProgress.postValue(false)
            }
            is NewsInteractorState.Failed.GenericError -> {
                mutableNewsList.postValue(null)
                mutableErrorMessage.postValue(state.throwable.message)
                mutableShouldShowProgress.postValue(false)
            }
        }
    }

    fun getTopHeadlines() {
        newsInteractor.pushUpdate(NewsInteractorUpdate.GetTopHeadlinesRequest("in"))
    }
}
