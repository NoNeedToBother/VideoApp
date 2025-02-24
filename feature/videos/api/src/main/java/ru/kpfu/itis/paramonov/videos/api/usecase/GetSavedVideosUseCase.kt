package ru.kpfu.itis.paramonov.videos.api.usecase

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import java.util.Date

interface GetSavedVideosUseCase {

    suspend operator fun invoke(limit: Int, after: Date): List<VideoModel>

}