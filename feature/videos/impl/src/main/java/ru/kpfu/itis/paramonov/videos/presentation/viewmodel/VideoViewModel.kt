package ru.kpfu.itis.paramonov.videos.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.paramonov.core.resources.ResourceManager
import ru.kpfu.itis.paramonov.videos.R
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideoUseCase
import ru.kpfu.itis.paramonov.videos.domain.mapper.VideoUiModelMapper
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenState

class VideoViewModel(
    private val getSavedVideoUseCase: GetSavedVideoUseCase,
    private val videoUiModelMapper: VideoUiModelMapper,
    private val player: Player,
    private val resourceManager: ResourceManager,
): ViewModel(), ContainerHost<VideoScreenState, VideoScreenSideEffect> {

    override val container = container<VideoScreenState, VideoScreenSideEffect>(
        VideoScreenState(player = player)
    )

    fun onIntent(intent: VideoScreenIntent) = intent {
        when(intent) {
            is VideoScreenIntent.GetVideo -> getVideo(intent.id)
        }
    }

    private fun getVideo(id: Long) = intent {
        try {
            val video = getSavedVideoUseCase.invoke(id)
            reduce {
                state.copy(video = videoUiModelMapper.map(video))
            }

            withContext(Dispatchers.Main) {
                player.addMediaItem(MediaItem.fromUri(
                    Uri.parse(video.videoUrl)
                ))
                player.prepare()
                player.playWhenReady = true
            }
            reduce {
                state.copy(player = player)
            }
        } catch (ex: Throwable) {
            postSideEffect(
                VideoScreenSideEffect.ShowError(
                    resourceManager.getString(R.string.empty),
                    resourceManager.getString(R.string.default_error)
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}