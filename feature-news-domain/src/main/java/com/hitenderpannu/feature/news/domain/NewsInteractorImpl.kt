package com.hitenderpannu.feature.news.domain

import com.hitenderpannu.feature.news.entity.NewsResult
import com.hitenderpannu.feature.news.remote.NewsRemoteRepo
import com.tinder.StateMachine
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class NewsInteractorImpl(
    private val newsRemoteRepo: NewsRemoteRepo
) : NewsInteractor {

    private var interactorState: BehaviorSubject<NewsInteractorState> = BehaviorSubject.create()

    private val stateMachine = StateMachine.create<
        NewsInteractorState, NewsInteractorUpdate, NewsInteractorSideEffect> {

        initialState(NewsInteractorState.Init)

        state<NewsInteractorState.Init> {
            on<NewsInteractorUpdate.GetTopHeadlinesRequest> {
                transitionTo(NewsInteractorState.Loading(it.countryCode))
            }
        }
        state<NewsInteractorState.Loading> {
            on<NewsInteractorUpdate.GotFailure> {
                when (it.error) {
                    is NewsResult.Failure.ApiFailure -> transitionTo(NewsInteractorState.Failed.ApiError(it.error.message, it.error.errorCode))
                    is NewsResult.Failure.GenericFailure -> transitionTo(NewsInteractorState.Failed.GenericError(it.error.error))
                }
            }
            on<NewsInteractorUpdate.GotSuccess> {
                transitionTo(NewsInteractorState.Success(it.success.articles))
            }
        }
        state<NewsInteractorState.Success> {
            on<NewsInteractorUpdate.GetTopHeadlinesRequest> {
                transitionTo(NewsInteractorState.Loading(it.countryCode))
            }
        }
        state<NewsInteractorState.Failed> {
            on<NewsInteractorUpdate.GetTopHeadlinesRequest> {
                transitionTo(NewsInteractorState.Loading(it.countryCode))
            }
        }
        onTransition {
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
            println(it.toState.toString())
            when (val it = validTransition.toState) {
                is NewsInteractorState.Loading -> requestTopHeadlines(it.fetchingResultForCountryCode)
                else -> {
                }
            }
        }
    }

    override fun observeState(): Observable<NewsInteractorState> {
        return interactorState
    }

    override fun pushUpdate(update: NewsInteractorUpdate) {
        stateMachine.transition(update)
    }

    private fun requestTopHeadlines(countryCode: String) {
        newsRemoteRepo.getTopHeadlines(countryCode)
            .subscribeOn(Schedulers.io())
            .subscribe { response, error ->
                if (error != null) {
                    stateMachine.transition(NewsInteractorUpdate.GotFailure(NewsResult.Failure.GenericFailure(error = error)))
                } else {
                    when (response) {
                        is NewsResult.Success -> stateMachine.transition(NewsInteractorUpdate.GotSuccess(NewsResult.Success(response.articles)))
                        is NewsResult.Failure -> stateMachine.transition(NewsInteractorUpdate.GotFailure(response))
                    }
                }
            }
    }
}
