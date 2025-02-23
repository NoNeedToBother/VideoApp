package ru.kpfu.itis.paramonov.videos.api.repository

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel

interface YouTubeVideoRepository {

    suspend fun getMostPopularVideos(): List<VideoModel>

}