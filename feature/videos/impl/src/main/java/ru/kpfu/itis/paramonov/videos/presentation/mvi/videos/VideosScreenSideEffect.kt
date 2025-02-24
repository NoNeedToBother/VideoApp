package ru.kpfu.itis.paramonov.videos.presentation.mvi.videos

sealed interface VideosScreenSideEffect {
    data class ShowError(val title: String, val message: String): VideosScreenSideEffect
}