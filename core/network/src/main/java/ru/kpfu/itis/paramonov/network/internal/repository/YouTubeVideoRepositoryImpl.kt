package ru.kpfu.itis.paramonov.network.internal.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.network.external.model.YouTubeVideoModel
import ru.kpfu.itis.paramonov.network.external.repository.YouTubeVideoRepository
import ru.kpfu.itis.paramonov.network.internal.remote.YouTubeApi
import ru.kpfu.itis.paramonov.network.internal.mapper.YouTubeVideoModelMapper

internal class YouTubeVideoRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val mapper: YouTubeVideoModelMapper,
    private val youTubeApi: YouTubeApi
): YouTubeVideoRepository {
    override suspend fun getMostPopularVideos(): List<YouTubeVideoModel> {
        return withContext(dispatcher) {
            mapper.map(youTubeApi.getMostPopularVideos())
        }
    }
}