package ru.kpfu.itis.paramonov.videos.presentation.model

data class VideoUiModel(
    val id: Long,
    val title: String,
    val duration: Int,
    val videoUrl: String,
    val height: Int,
    val width: Int,
    val thumbnailUrl: String,
)
