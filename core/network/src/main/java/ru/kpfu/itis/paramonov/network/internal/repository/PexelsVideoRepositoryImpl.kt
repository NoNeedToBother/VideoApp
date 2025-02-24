package ru.kpfu.itis.paramonov.network.internal.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.network.external.model.PexelsVideoModel
import ru.kpfu.itis.paramonov.network.external.repository.PexelsVideoRepository
import ru.kpfu.itis.paramonov.network.internal.remote.PexelsApi
import ru.kpfu.itis.paramonov.network.internal.mapper.PexelsVideoModelMapper

internal class PexelsVideoRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val mapper: PexelsVideoModelMapper,
    private val pexelsApi: PexelsApi
): PexelsVideoRepository {
    override suspend fun getMostPopularVideos(limit: Int): List<PexelsVideoModel> {
        return withContext(dispatcher) {
            mapper.map(pexelsApi.getMostPopularVideos(limit = limit))
        }
    }
}