package com.mbt925.realestate.core.domain

typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State

fun <State, Effect> reducer(body: State.(Effect) -> State) = object : Reducer<State, Effect> {

    override fun invoke(
        state: State,
        effect: Effect,
    ): State = body(state, effect)
}
