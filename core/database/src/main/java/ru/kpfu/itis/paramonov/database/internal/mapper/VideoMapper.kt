package ru.kpfu.itis.paramonov.database.internal.mapper

import ru.kpfu.itis.paramonov.database.external.model.Video
import ru.kpfu.itis.paramonov.database.external.model.VideoStatistics
import ru.kpfu.itis.paramonov.database.internal.converter.fromDurationToString
import ru.kpfu.itis.paramonov.database.internal.converter.fromStringToDuration
import ru.kpfu.itis.paramonov.database.internal.entity.VideoEntity

internal class VideoMapper {

    fun fromEntity(entity: VideoEntity): Video {
        return Video(
            id = entity.youTubeId,
            title = entity.title,
            description = entity.description,
            duration = fromStringToDuration(entity.duration),
            statistics = VideoStatistics(
                viewCount = entity.viewCount,
                likeCount = entity.likeCount,
            ),
            defaultThumbnailUrl = entity.defaultThumbnailUrl,
            standardThumbnailUrl = entity.standardThumbnailUrl,
        )
    }

    fun toEntity(model: Video): VideoEntity {
        return VideoEntity(
            youTubeId = model.id,
            title = model.title,
            description = model.description,
            duration = fromDurationToString(model.duration),
            viewCount = model.statistics.viewCount,
            likeCount = model.statistics.likeCount,
            defaultThumbnailUrl = model.defaultThumbnailUrl,
            standardThumbnailUrl = model.standardThumbnailUrl,
        )
    }
}