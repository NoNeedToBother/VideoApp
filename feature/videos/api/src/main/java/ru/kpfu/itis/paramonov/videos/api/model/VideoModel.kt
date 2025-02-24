package ru.kpfu.itis.paramonov.videos.api.model

data class VideoModel(
    val id: Long,
    val title: String,
    val duration: Int,
    val videoUrl: String,
    val height: Int,
    val width: Int,
    val thumbnailUrl: String,
)

