package ru.kpfu.itis.paramonov.videos.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.repository.SavedVideoRepository
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideosUseCase
import java.util.Date

class GetSavedVideosUseCaseImpl(
    private val dispatcher: CoroutineDispatcher,
    private val savedVideoRepository: SavedVideoRepository
): GetSavedVideosUseCase {
    override suspend fun invoke(limit: Int, after: Date): List<VideoModel> {
        return withContext(dispatcher) {
            savedVideoRepository.getLatestVideos(limit, after)
        }
    }
}