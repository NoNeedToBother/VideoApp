package ru.kpfu.itis.paramonov.videos.presentation.mvi.videos

import java.util.Date

sealed interface VideosScreenIntent {
    data class GetMostPopularVideos(val limit: Int, val after: Date): VideosScreenIntent
}