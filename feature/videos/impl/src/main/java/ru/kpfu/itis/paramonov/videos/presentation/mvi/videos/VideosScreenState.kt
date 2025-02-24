package ru.kpfu.itis.paramonov.videos.presentation.mvi.videos

import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel

data class VideosScreenState(
    val videos: List<VideoUiModel> = emptyList(),
    val isRefreshing: Boolean = false
)