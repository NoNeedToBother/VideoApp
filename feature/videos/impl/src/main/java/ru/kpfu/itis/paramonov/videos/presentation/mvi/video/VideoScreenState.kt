package ru.kpfu.itis.paramonov.videos.presentation.mvi.video

import androidx.media3.common.Player
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel

data class VideoScreenState(
    val video: VideoUiModel? = null,
    val player: Player,
)
