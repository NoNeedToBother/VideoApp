package ru.kpfu.itis.paramonov.videos.domain.mapper

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoStatisticsUiModel
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel

class VideoUiModelMapper {

    fun map(model: VideoModel): VideoUiModel {
        return VideoUiModel(
            id = model.id,
            title = model.title,
            description = model.description,
            duration = model.duration,
            statistics = VideoStatisticsUiModel(
                viewCount = model.statistics.viewCount,
                likeCount = model.statistics.likeCount,
            ),
            defaultThumbnailUrl = model.defaultThumbnailUrl,
            standardThumbnailUrl = model.standardThumbnailUrl,
        )
    }
}