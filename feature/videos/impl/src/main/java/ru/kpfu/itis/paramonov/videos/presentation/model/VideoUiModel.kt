package ru.kpfu.itis.paramonov.videos.presentation.model

import kotlin.time.Duration

data class VideoUiModel(
    val id: String,
    val title: String,
    val description: String,
    val duration: Duration,
    val statistics: VideoStatisticsUiModel,
    val defaultThumbnailUrl: String,
    val standardThumbnailUrl: String,
)

data class VideoStatisticsUiModel(
    val viewCount: Int,
    val likeCount: Int,
)