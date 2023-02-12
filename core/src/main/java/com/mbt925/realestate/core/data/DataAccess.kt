package com.mbt925.realestate.core.data

import kotlinx.coroutines.flow.Flow

/**
 * Handling remote and local data should be done in a unified way.
 * This interface exposes different strategies to read data from local and remote sources.
 * Data is always stored locally, and then returned
 */
interface DataAccess<T> {

    /**
     * @return local data if present or null
     */
    suspend fun get(): T?

    /**
     * @return local data if present, or otherwise fetched remote data
     */
    suspend fun getOrFetch(): T

    /**
     * @return fetched remote data, after clearing local data
     */
    suspend fun clearAndFetch(): T

    /**
     * @return A flow which upon collecting first emits local data if present,
     * then fetches remote data and emits the fetched data.
     */
    fun getAndFetch(): Flow<T>

    /**
     * @return A flow which upon collection, first emits local data if present,
     * then keeps fetching and emitting remote data as long as the collecting
     * coroutine job is active.
     */
    fun poll(interval: Long = DEFAULT_POLLING_INTERVAL): Flow<T>

    companion object {
        const val DEFAULT_POLLING_INTERVAL = 15_000L
    }
}

/**
 * Factory method to instantiate [DataAccess] with mapping between local and remote types.
 * Saving and getting locals is delegated.
 */
operator fun <Data> DataAccess.Companion.invoke(
    log: (String) -> Unit = {},
    get: suspend () -> Data?,
    save: suspend (Data?) -> Boolean,
    fetch: suspend () -> Data,
): DataAccess<Data> = DataAccessImpl(
    log = log,
    get = get,
    save = save,
    fetch = fetch,
)

/**
 * Factory method to instantiate [DataAccess].
 * Fetched data gets saved to and read from memory.
 */
operator fun <Remote> DataAccess.Companion.invoke(
    log: (String) -> Unit = {},
    fetch: suspend () -> Remote,
): DataAccess<Remote> {
    var data: Remote? = null
    return DataAccess(
        get = { data },
        save = { data = it; true },
        fetch = fetch,
        log = log,
    )
}
