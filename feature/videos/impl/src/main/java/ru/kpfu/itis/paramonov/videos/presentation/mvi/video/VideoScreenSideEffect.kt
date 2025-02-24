package ru.kpfu.itis.paramonov.videos.presentation.mvi.video

sealed interface VideoScreenSideEffect {
    data class ShowError(val message: String): VideoScreenSideEffect
}
