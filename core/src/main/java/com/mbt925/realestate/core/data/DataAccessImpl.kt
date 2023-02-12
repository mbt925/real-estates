package com.mbt925.realestate.core.data

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive

internal class DataAccessImpl<Data>(
    val log: (String) -> Unit,
    val get: suspend () -> Data?,
    val save: suspend (Data?) -> Boolean,
    val fetch: suspend () -> Data,
) : DataAccess<Data> {

    private val isFetching = AtomicBoolean(false)

    private fun <Data> Flow<Data>.flowLogging() =
        onStart { log("Starting flow") }
            .onEach { log("Emitting value: $it") }
            .onCompletion { log("Ending flow") }

    private suspend fun FlowCollector<Data>.emitIfNotNull(): Data? {
        log("Checking local data")
        return get()
            ?.also { log("Found local data") }
            ?.also { emit(it) }
            .also { if (it == null) log("No local data found") }
    }

    private suspend fun fetchAndSave(
        onSaved: suspend () -> Unit = {},
        onNotSaved: suspend (Data) -> Unit = {},
    ) {
        if (isFetching.compareAndSet(false, true)) {
            try {
                log("Fetching remote data")
                fetch()
                    .also { log("Saving remote value: $it") }
                    .also {
                        if (save(it)) onSaved() else onNotSaved(it)
                    }
            } finally {
                isFetching.set(false)
            }
        } else {
            log("Skip fetching remote data as call is already running")
            while (isFetching.get()) {
                delay(10) // wait to ensure further calls have the updated local state
            }
        }
    }

    private suspend fun FlowCollector<Data>.fetchSaveEmit() {
        fetchAndSave(
            onSaved = { emitIfNotNull() },
            onNotSaved = { emit(it) }
        )
    }

    private suspend fun clear() {
        log("Clear local data")
        save(null)
    }

    override suspend fun get() = get.invoke()
    override suspend fun getOrFetch() = flow { emitIfNotNull() ?: fetchSaveEmit() }
        .flowLogging()
        .first()

    override suspend fun clearAndFetch() = flow { fetchSaveEmit() }
        .onStart { clear() }
        .flowLogging()
        .first()

    override fun getAndFetch() = flow { fetchSaveEmit() }
        .onStart { emitIfNotNull() }
        .flowLogging()

    override fun poll(interval: Long) = flow {
        while (currentCoroutineContext().isActive) {
            fetchSaveEmit()
            delay(interval)
        }
    }
        .onStart { emitIfNotNull() }
        .flowLogging()
}
