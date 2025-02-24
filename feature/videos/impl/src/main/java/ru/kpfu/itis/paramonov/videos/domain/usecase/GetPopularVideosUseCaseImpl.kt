package ru.kpfu.itis.paramonov.videos.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.repository.PexelsVideoRepository
import ru.kpfu.itis.paramonov.videos.api.usecase.GetPopularPexelsVideosUseCase

class GetPopularVideosUseCaseImpl(
    private val dispatcher: CoroutineDispatcher,
    private val youTubeVideoRepository: PexelsVideoRepository
): GetPopularPexelsVideosUseCase {

    override suspend fun invoke(limit: Int): List<VideoModel> {
        return withContext(dispatcher) {
            youTubeVideoRepository.getMostPopularVideos(limit)
        }
    }
}