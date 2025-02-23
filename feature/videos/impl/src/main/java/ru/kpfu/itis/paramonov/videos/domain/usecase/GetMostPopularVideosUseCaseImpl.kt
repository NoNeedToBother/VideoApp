package ru.kpfu.itis.paramonov.videos.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.repository.YouTubeVideoRepository
import ru.kpfu.itis.paramonov.videos.api.usecase.GetMostPopularYouTubeVideosUseCase

class GetMostPopularVideosUseCaseImpl(
    private val dispatcher: CoroutineDispatcher,
    private val youTubeVideoRepository: YouTubeVideoRepository
): GetMostPopularYouTubeVideosUseCase {

    override suspend fun invoke(): List<VideoModel> {
        return withContext(dispatcher) {
            youTubeVideoRepository.getMostPopularVideos()
        }
    }
}