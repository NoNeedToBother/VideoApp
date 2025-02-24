package ru.kpfu.itis.paramonov.videos.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.repository.SavedVideoRepository
import ru.kpfu.itis.paramonov.videos.api.usecase.SaveVideosUseCase

class SaveVideosUseCaseImpl(
    private val dispatcher: CoroutineDispatcher,
    private val savedVideoRepository: SavedVideoRepository,
): SaveVideosUseCase {
    override suspend fun invoke(videos: List<VideoModel>) {
        return withContext(dispatcher) {
            savedVideoRepository.saveVideos(videos)
        }
    }
}
