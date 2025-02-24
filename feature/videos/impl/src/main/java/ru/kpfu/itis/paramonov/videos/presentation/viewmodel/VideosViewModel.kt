package ru.kpfu.itis.paramonov.videos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.paramonov.videos.api.usecase.GetPopularPexelsVideosUseCase
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideosUseCase
import ru.kpfu.itis.paramonov.videos.api.usecase.SaveVideosUseCase
import ru.kpfu.itis.paramonov.videos.domain.mapper.VideoUiModelMapper
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenState
import java.util.ArrayList
import java.util.Date

class VideosViewModel(
    private val getMostPopularYouTubeVideosUseCase: GetPopularPexelsVideosUseCase,
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
                val videos = getMostPopularYouTubeVideosUseCase.invoke(limit).take(limit - saved.size)
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
            //when(ex) {

            //}
        } finally {
            delay(100L) //otherwise pull to refresh indicator stays on screen
            reduce { state.copy(isRefreshing = false) }
        }
    }
}