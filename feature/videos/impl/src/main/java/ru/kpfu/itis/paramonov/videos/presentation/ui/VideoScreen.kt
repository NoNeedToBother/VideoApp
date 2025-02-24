package ru.kpfu.itis.paramonov.videos.presentation.ui

import android.view.LayoutInflater
import android.view.Surface
import androidx.compose.foundation.AndroidEmbeddedExternalSurface
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.foundation.AndroidExternalSurfaceScope
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import ru.kpfu.itis.paramonov.videos.R
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideoViewModel

@Composable
fun VideoScreen(
    id: Long
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

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
        }
    }

    VideoScreenContent(
        modifier = Modifier.fillMaxWidth(),
        video = state.value.video,
        lifecycle = lifecycle,
        player = state.value.player
    )
}

@Composable
fun VideoScreenContent(
    modifier: Modifier = Modifier,
    video: VideoUiModel?,
    lifecycle: Lifecycle.Event,
    player: Player
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.scrollable(
            state = scrollState,
            orientation = Orientation.Vertical
        )
    ) {
        video?.let {
            Player(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                player = player,
                video = it,
                lifecycle = lifecycle
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                text = video.title,
                textAlign = TextAlign.Center,
            )

        } ?: CircularProgressIndicator(
            modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
        )
    }

}

@Composable
fun PlayerSurface(player: Player, modifier: Modifier = Modifier) {
    val onSurfaceCreated: (Surface) -> Unit = { surface -> player.setVideoSurface(surface) }
    val onSurfaceDestroyed: () -> Unit = { player.setVideoSurface(null) }
    val onSurfaceInitialized: AndroidExternalSurfaceScope.() -> Unit = {
        onSurface { surface, _, _ ->
            onSurfaceCreated(surface)
            surface.onDestroyed { onSurfaceDestroyed() }
        }
    }

    AndroidEmbeddedExternalSurface(modifier = modifier, onInit = onSurfaceInitialized)
}

@Composable
fun Player(
    modifier: Modifier = Modifier,
    player: Player,
    video: VideoUiModel,
    lifecycle: Lifecycle.Event
) {
    AndroidView(
        factory = { context ->
            (LayoutInflater.from(context).inflate(
                R.layout.player_surface_layout,
                null
            ) as PlayerView).also {
                it.player = player
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }
                else -> Unit
            }
        },
        modifier = modifier
            .padding(horizontal = 48.dp)
            .aspectRatio(video.width.toFloat() / video.height)
    )
}
