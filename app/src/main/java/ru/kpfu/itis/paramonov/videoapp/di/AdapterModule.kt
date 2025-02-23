package ru.kpfu.itis.paramonov.videoapp.di

import org.koin.dsl.module
import ru.kpfu.itis.paramonov.network.external.repository.YouTubeVideoRepository
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.model.VideoStatistics
import ru.kpfu.itis.paramonov.videos.api.repository.YouTubeVideoRepository as FeatureYouTubeVideoRepository

val adapterModule = module {
    factory<FeatureYouTubeVideoRepository> {
        val youTubeVideoRepository: YouTubeVideoRepository = get()
        object : FeatureYouTubeVideoRepository {
            override suspend fun getMostPopularVideos(): List<VideoModel> {
                return youTubeVideoRepository.getMostPopularVideos().map {
                    VideoModel(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        duration = it.duration,
                        statistics = VideoStatistics(
                            viewCount = it.statistics.viewCount,
                            likeCount = it.statistics.likeCount,
                        ),
                        defaultThumbnailUrl = it.defaultThumbnailUrl,
                        standardThumbnailUrl = it.standardThumbnailUrl,
                    )
                }
            }
        }
    }
}
