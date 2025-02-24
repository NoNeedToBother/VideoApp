package ru.kpfu.itis.paramonov.database.external.repository

import ru.kpfu.itis.paramonov.database.external.model.Video
import java.util.Date

interface VideoRepository {

    suspend fun getLatestVideos(limit: Int, after: Date): List<Video>

    suspend fun getLatestVideoByYoutubeId(id: Long): Video

    suspend fun saveVideos(videos: List<Video>)

}