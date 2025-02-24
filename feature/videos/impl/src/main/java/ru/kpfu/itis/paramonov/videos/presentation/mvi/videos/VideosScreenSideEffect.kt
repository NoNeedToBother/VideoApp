package ru.kpfu.itis.paramonov.videos.presentation.mvi.videos

sealed interface VideosScreenSideEffect {
    data class ShowError(val message: String): VideosScreenSideEffect
}