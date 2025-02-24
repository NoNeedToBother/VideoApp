package ru.kpfu.itis.paramonov.database.external.model

data class Video(
    val id: Long,
    val title: String,
    val duration: Int,
    val videoUrl: String,
    val height: Int,
    val width: Int,
    val thumbnailUrl: String,
)
