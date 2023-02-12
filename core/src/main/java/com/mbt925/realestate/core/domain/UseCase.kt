package com.mbt925.realestate.core.domain

import kotlinx.coroutines.flow.Flow

interface UseCase<Effect> {
    suspend fun execute(): Flow<Effect>
}

fun <Effect> useCase(body: suspend () -> Flow<Effect>) = object : UseCase<Effect> {
    override suspend fun execute(): Flow<Effect> = body()
}
