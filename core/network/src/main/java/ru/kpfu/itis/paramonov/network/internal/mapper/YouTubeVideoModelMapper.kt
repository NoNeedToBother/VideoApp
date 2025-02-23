package ru.kpfu.itis.paramonov.network.internal.mapper

import ru.kpfu.itis.paramonov.network.external.model.YouTubeVideoModel
import ru.kpfu.itis.paramonov.network.external.model.YouTubeVideoStatistics
import ru.kpfu.itis.paramonov.network.internal.model.YouTubeVideosResponseModel
import kotlin.time.Duration

internal class YouTubeVideoModelMapper {

    fun map(videos: YouTubeVideosResponseModel): List<YouTubeVideoModel> {
        return videos.videos.map { video ->
            YouTubeVideoModel(
                id = video.id,
                title = video.snippet.title,
                description = video.snippet.description,
                duration = Duration.parse(video.details.duration),
                statistics = YouTubeVideoStatistics(
                    viewCount = video.statistics.viewCount,
                    likeCount = video.statistics.likeCount
                ),
                defaultThumbnailUrl = video.snippet.thumbnails.defaultThumbnail.url,
                standardThumbnailUrl = video.snippet.thumbnails.standardThumbnail.url,
            )
        }
    }
}