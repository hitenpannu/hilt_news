package com.hitenderpannu.feature.news.domain

import com.hitenderpannu.feature.news.entity.NewsArticle
import com.hitenderpannu.feature.news.entity.NewsResult

sealed class NewsInteractorState {
    object Init : NewsInteractorState()
    data class Loading(val fetchingResultForCountryCode: String) : NewsInteractorState()
    data class Success(val news: List<NewsArticle>) : NewsInteractorState()
    sealed class Failed : NewsInteractorState() {
        data class ApiError(val message: String, val code: String) : Failed()
        data class GenericError(val throwable: Throwable) : Failed()
    }
}

sealed class NewsInteractorUpdate {
    data class GetTopHeadlinesRequest(val countryCode: String) : NewsInteractorUpdate()
    data class GotFailure(val error: NewsResult.Failure) : NewsInteractorUpdate()
    data class GotSuccess(val success: NewsResult.Success) : NewsInteractorUpdate()
}

sealed class NewsInteractorSideEffect {

}
