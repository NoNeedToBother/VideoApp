package ru.kpfu.itis.paramonov.videos.presentation.mvi.video

sealed interface VideoScreenSideEffect {
    data class ShowError(val title: String, val message: String): VideoScreenSideEffect
}
