package ru.kpfu.itis.paramonov.videos.domain.mapper

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel

class VideoUiModelMapper {

    fun map(model: VideoModel): VideoUiModel {
        return VideoUiModel(
            id = model.id,
            title = model.title,
            duration = model.duration,
            videoUrl = model.videoUrl,
            height = model.height,
            width = model.width,
            thumbnailUrl = model.thumbnailUrl,
        )
    }
}
