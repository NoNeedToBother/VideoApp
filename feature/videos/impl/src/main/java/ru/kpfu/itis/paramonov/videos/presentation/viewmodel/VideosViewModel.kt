package ru.kpfu.itis.paramonov.videos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.paramonov.videos.api.usecase.GetMostPopularYouTubeVideosUseCase
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideosUseCase
import ru.kpfu.itis.paramonov.videos.api.usecase.SaveVideosUseCase
import ru.kpfu.itis.paramonov.videos.domain.mapper.VideoUiModelMapper
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenState
import java.util.ArrayList
import java.util.Date

class VideosViewModel(
    private val getMostPopularYouTubeVideosUseCase: GetMostPopularYouTubeVideosUseCase,
    private val saveVideosUseCase: SaveVideosUseCase,
    private val getSavedVideosUseCase: GetSavedVideosUseCase,
    private val videoUiModelMapper: VideoUiModelMapper,
): ViewModel(), ContainerHost<VideosScreenState, VideosScreenSideEffect> {

    override val container = container<VideosScreenState, VideosScreenSideEffect>(
        VideosScreenState()
    )

    fun onIntent(intent: VideosScreenIntent) = intent {
        when(intent) {
            is VideosScreenIntent.GetMostPopularVideos -> getMostPopularVideos(
                limit = intent.limit, after = intent.after
            )
        }
    }

    private fun getMostPopularVideos(limit: Int, after: Date) = intent {
        reduce { state.copy(isRefreshing = true) }
        try {
            val saved = getSavedVideosUseCase.invoke(limit, after)
            if (saved.size != limit) {
                val videos = getMostPopularYouTubeVideosUseCase.invoke().take(limit - saved.size)
                saveVideosUseCase.invoke(videos)

                val resultList = ArrayList(saved)
                resultList.addAll(videos)
                reduce {
                    state.copy(videos = resultList.map { videoUiModelMapper.map(it) })
                }
            } else {
                reduce {
                    state.copy(videos = saved.map { videoUiModelMapper.map(it) })
                }
            }
        } catch (ex: Throwable) {
            //postSideEffect()
        } finally {
            reduce { state.copy(isRefreshing = false) }
        }
    }
}