package ru.kpfu.itis.paramonov.videos.api.repository

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel

interface PexelsVideoRepository {

    suspend fun getMostPopularVideos(limit: Int): List<VideoModel>

}