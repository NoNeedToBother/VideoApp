package ru.kpfu.itis.paramonov.videos.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideoViewModel

@Composable
fun VideoScreen(
    id: String
) {
    val viewModel = koinInject<VideoViewModel>()

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    LaunchedEffect(Unit) {
        viewModel.onIntent(
            VideoScreenIntent.GetVideo(id = id))

        effect.collect {
            when(it) {
                is VideoScreenSideEffect.ShowError -> {}
            }
        }
    }

    VideoScreenContent(
        modifier = Modifier.fillMaxSize(),
        video = state.value.video
    )
}

@Composable
fun VideoScreenContent(
    modifier: Modifier = Modifier,
    video: VideoUiModel?,
) {
    video?.let {
        Text(text = video.title)
    }

}
