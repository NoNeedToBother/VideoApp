package ru.kpfu.itis.paramonov.videos.api.usecase

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel

interface GetPopularPexelsVideosUseCase {

    suspend operator fun invoke(limit: Int): List<VideoModel>

}