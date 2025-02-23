package ru.kpfu.itis.paramonov.network.external.model

import kotlin.time.Duration

data class YouTubeVideoModel(
    val id: String,
    val title: String,
    val description: String,
    val duration: Duration,
    val statistics: YouTubeVideoStatistics,
    val defaultThumbnailUrl: String,
    val standardThumbnailUrl: String,
)

data class YouTubeVideoStatistics(
    val viewCount: Int,
    val likeCount: Int,
)
