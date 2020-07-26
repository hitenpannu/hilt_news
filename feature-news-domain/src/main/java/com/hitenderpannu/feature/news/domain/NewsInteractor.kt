package com.hitenderpannu.feature.news.domain

import com.hitenderpannu.domain.BaseInteractor

interface NewsInteractor : BaseInteractor<NewsInteractorState, NewsInteractorUpdate>{

    fun pushUpdate(update: NewsInteractorUpdate)

}
