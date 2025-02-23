package ru.kpfu.itis.paramonov.videos.presentation.mvi

sealed interface VideosScreenIntent {
    data object GetMostPopularVideos: VideosScreenIntent
}