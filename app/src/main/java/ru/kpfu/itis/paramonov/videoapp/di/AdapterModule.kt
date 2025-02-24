package ru.kpfu.itis.paramonov.videoapp.di

import org.koin.dsl.module
import ru.kpfu.itis.paramonov.database.external.model.Video
import ru.kpfu.itis.paramonov.database.external.repository.VideoRepository
import ru.kpfu.itis.paramonov.network.external.repository.YouTubeVideoRepository
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.model.VideoStatistics
import ru.kpfu.itis.paramonov.videos.api.repository.YouTubeVideoRepository as FeatureYouTubeVideoRepository
import ru.kpfu.itis.paramonov.videos.api.repository.SavedVideoRepository
import java.util.Date

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

    factory<SavedVideoRepository> { 
        val repository: VideoRepository = get()
        object : SavedVideoRepository {
            override suspend fun getLatestVideos(limit: Int, after: Date): List<VideoModel> {
                return repository.getLatestVideos(limit, after).map {
                    mapSavedVideoToFeatureVideosVideoModel(it)
                }
            }

            override suspend fun getLatestVideoByYoutubeId(id: String): VideoModel {
                return mapSavedVideoToFeatureVideosVideoModel(
                    repository.getLatestVideoByYoutubeId(id)
                )
            }

            override suspend fun saveVideos(videos: List<VideoModel>) {
                repository.saveVideos(videos.map {
                    mapFeatureVideosVideoModelToSavedVideo(it)
                })
            }
        }
    }
}

fun mapSavedVideoToFeatureVideosVideoModel(model: Video): VideoModel {
    return VideoModel(
        id = model.id,
        title = model.title,
        description = model.description,
        duration = model.duration,
        statistics = VideoStatistics(
            viewCount = model.statistics.viewCount,
            likeCount = model.statistics.likeCount
        ),
        defaultThumbnailUrl = model.defaultThumbnailUrl,
        standardThumbnailUrl = model.standardThumbnailUrl,
    )
}

fun mapFeatureVideosVideoModelToSavedVideo(model: VideoModel): Video {
    return Video(
        id = model.id,
        title = model.title,
        description = model.description,
        duration = model.duration,
        statistics = ru.kpfu.itis.paramonov.database.external.model.VideoStatistics(
            viewCount = model.statistics.viewCount,
            likeCount = model.statistics.likeCount,
        ),
        defaultThumbnailUrl = model.defaultThumbnailUrl,
        standardThumbnailUrl = model.standardThumbnailUrl,
    )
}
