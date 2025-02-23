package ru.kpfu.itis.paramonov.videos.presentation.mvi

sealed interface VideosScreenSideEffect {
    data class ShowError(val message: String): VideosScreenSideEffect
}