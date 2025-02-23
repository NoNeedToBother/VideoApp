package ru.kpfu.itis.paramonov.network.external.repository

import ru.kpfu.itis.paramonov.network.external.model.YouTubeVideoModel

interface YouTubeVideoRepository {

    suspend fun getMostPopularVideos(): List<YouTubeVideoModel>

}