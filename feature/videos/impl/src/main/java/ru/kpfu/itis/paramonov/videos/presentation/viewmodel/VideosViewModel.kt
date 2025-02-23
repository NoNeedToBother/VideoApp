package ru.kpfu.itis.paramonov.videos.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.paramonov.videos.api.usecase.GetMostPopularYouTubeVideosUseCase
import ru.kpfu.itis.paramonov.videos.domain.mapper.VideoUiModelMapper
import ru.kpfu.itis.paramonov.videos.presentation.mvi.VideosScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.VideosScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.mvi.VideosScreenState

class VideosViewModel(
    private val getMostPopularYouTubeVideosUseCase: GetMostPopularYouTubeVideosUseCase,
    private val videoUiModelMapper: VideoUiModelMapper,
): ViewModel(), ContainerHost<VideosScreenState, VideosScreenSideEffect> {

    override val container = container<VideosScreenState, VideosScreenSideEffect>(
        VideosScreenState()
    )

    fun onIntent(intent: VideosScreenIntent) = intent {
        when(intent) {
            VideosScreenIntent.GetMostPopularVideos -> getMostPopularVideos()
        }
    }

    private fun getMostPopularVideos() = intent {
        reduce { state.copy(isRefreshing = true) }
        try {
            val videos = getMostPopularYouTubeVideosUseCase.invoke()
            reduce {
                state.copy(videos = videos.map { videoUiModelMapper.map(it) })
            }
        } catch (ex: Throwable) {
            Log.i("BRUUUUUUUUUH", ex.message ?: "")
            //postSideEffect()
        } finally {
            reduce { state.copy(isRefreshing = false) }
        }
    }
}