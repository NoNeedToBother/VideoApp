package ru.kpfu.itis.paramonov.videos.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.repository.SavedVideoRepository
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideoUseCase

class GetSavedVideoUseCaseImpl(
    private val dispatcher: CoroutineDispatcher,
    private val savedVideoRepository: SavedVideoRepository
): GetSavedVideoUseCase {
    override suspend fun invoke(id: Long): VideoModel {
        return withContext(dispatcher) {
            savedVideoRepository.getLatestVideoByYoutubeId(id)
        }
    }
}