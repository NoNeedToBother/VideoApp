package ru.kpfu.itis.paramonov.videos.presentation.mvi.video

sealed interface VideoScreenIntent {
    data class GetVideo(val id: Long): VideoScreenIntent
    data object ClearVideo: VideoScreenIntent
}
