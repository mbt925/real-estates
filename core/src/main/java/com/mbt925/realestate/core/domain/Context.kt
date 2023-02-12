package com.mbt925.realestate.core.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface ContextReducer<State> {
    fun reduce(reducer: (State) -> State)
}

interface ContextState<State> {
    val state: StateFlow<State>
}

interface ContextExecutor<State> {
    suspend fun <Effect> execute(
        useCase: UseCase<Effect>,
        reducer: Reducer<State, Effect>,
    )
}

interface Context<State> : ContextReducer<State>, ContextState<State>, ContextExecutor<State> {

    override suspend fun <Effect> execute(
        useCase: UseCase<Effect>,
        reducer: Reducer<State, Effect>,
    ) {
        useCase.execute().collect { effect ->
            reduce { reducer(it, effect) }
        }
    }

    companion object {
        operator fun <State> invoke(
            initialState: State,
        ): Context<State> = ContextImpl(
            initialState = initialState,
        )
    }
}

internal class ContextImpl<State>(
    initialState: State,
) : Context<State> {

    override val state: MutableStateFlow<State> = MutableStateFlow(initialState)

    override fun reduce(reducer: (State) -> State) {
        state.update { state -> reducer(state) }
    }
}
