package ru.kpfu.itis.paramonov.network.external.model

data class PexelsVideoModel(
    val id: Long,
    val title: String,
    val duration: Int,
    val videoUrl: String,
    val height: Int,
    val width: Int,
    val thumbnailUrl: String,
)
