package ru.kpfu.itis.paramonov.videos.api.repository

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel
import java.util.Date

interface SavedVideoRepository {

    suspend fun getLatestVideos(limit: Int, after: Date): List<VideoModel>

    suspend fun getLatestVideoByYoutubeId(id: Long): VideoModel

    suspend fun saveVideos(videos: List<VideoModel>)

}