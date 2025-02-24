package ru.kpfu.itis.paramonov.database.internal.mapper

import ru.kpfu.itis.paramonov.database.external.model.Video
import ru.kpfu.itis.paramonov.database.internal.entity.VideoEntity

internal class VideoMapper {

    fun fromEntity(entity: VideoEntity): Video {
        return Video(
            id = entity.pexelsId,
            title = entity.title,
            duration = entity.duration,
            videoUrl = entity.videoUrl,
            height = entity.height,
            width = entity.width,
            thumbnailUrl = entity.thumbnailUrl,
        )
    }

    fun toEntity(model: Video): VideoEntity {
        return VideoEntity(
            pexelsId = model.id,
            title = model.title,
            duration = model.duration,
            videoUrl = model.videoUrl,
            height = model.height,
            width = model.width,
            thumbnailUrl = model.thumbnailUrl,
        )
    }
}