package com.hitenderpannu.domain

import io.reactivex.rxjava3.core.Observable

interface BaseInteractor<State, Update> {

    fun observeState(): Observable<State>
}
