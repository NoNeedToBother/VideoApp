package ru.kpfu.itis.paramonov.videos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideoUseCase
import ru.kpfu.itis.paramonov.videos.domain.mapper.VideoUiModelMapper
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenState

class VideoViewModel(
    private val getSavedVideoUseCase: GetSavedVideoUseCase,
    private val videoUiModelMapper: VideoUiModelMapper,
): ViewModel(), ContainerHost<VideoScreenState, VideoScreenSideEffect> {

    override val container = container<VideoScreenState, VideoScreenSideEffect>(
        VideoScreenState()
    )

    fun onIntent(intent: VideoScreenIntent) = intent {
        when(intent) {
            is VideoScreenIntent.GetVideo -> getVideo(intent.id)
        }
    }

    private fun getVideo(id: String) = intent {
        try {
            val video = getSavedVideoUseCase.invoke(id)
            reduce {
                state.copy(video = videoUiModelMapper.map(video))
            }
        } catch (ex: Throwable) {
            //postSideEffect()
        }
    }
}