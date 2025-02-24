package ru.kpfu.itis.paramonov.videoapp.di

import org.koin.dsl.module
import ru.kpfu.itis.paramonov.database.external.model.Video
import ru.kpfu.itis.paramonov.database.external.repository.VideoRepository
import ru.kpfu.itis.paramonov.network.external.exception.HttpException
import ru.kpfu.itis.paramonov.network.external.repository.PexelsVideoRepository
import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import ru.kpfu.itis.paramonov.videos.api.repository.PexelsVideoRepository as FeaturePexelsVideoRepository
import ru.kpfu.itis.paramonov.videos.api.repository.SavedVideoRepository
import java.util.Date

val adapterModule = module {
    factory<FeaturePexelsVideoRepository> {
        val pexelsVideoRepository: PexelsVideoRepository = get()
        object : FeaturePexelsVideoRepository {
            override suspend fun getMostPopularVideos(limit: Int): List<VideoModel> {
                try {
                    return pexelsVideoRepository.getMostPopularVideos(limit).map {
                        VideoModel(
                            id = it.id,
                            title = it.title,
                            duration = it.duration,
                            thumbnailUrl = it.thumbnailUrl,
                            videoUrl = it.videoUrl,
                            height = it.height,
                            width = it.width
                        )
                    }
                } catch (ex: HttpException) {
                    throw ru.kpfu.itis.paramonov.videos.api.exception.HttpException(code = ex.code)
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

            override suspend fun getLatestVideoByYoutubeId(id: Long): VideoModel {
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
        duration = model.duration,
        thumbnailUrl = model.thumbnailUrl,
        videoUrl = model.videoUrl,
        height = model.height,
        width = model.width,
    )
}

fun mapFeatureVideosVideoModelToSavedVideo(model: VideoModel): Video {
    return Video(
        id = model.id,
        title = model.title,
        duration = model.duration,
        thumbnailUrl = model.thumbnailUrl,
        videoUrl = model.videoUrl,
        height = model.height,
        width = model.width,
    )
}
