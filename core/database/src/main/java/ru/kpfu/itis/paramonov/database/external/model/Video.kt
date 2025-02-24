package ru.kpfu.itis.paramonov.database.external.model

import kotlin.time.Duration

data class Video(
    val id: String,
    val title: String,
    val description: String,
    val duration: Duration,
    val statistics: VideoStatistics,
    val defaultThumbnailUrl: String,
    val standardThumbnailUrl: String,
)

data class VideoStatistics(
    val viewCount: Int,
    val likeCount: Int,
)