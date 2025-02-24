package ru.kpfu.itis.paramonov.videos.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.view.LayoutInflater
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import org.koin.compose.koinInject
import ru.kpfu.itis.paramonov.videos.R
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.video.VideoScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideoViewModel

@Composable
fun VideoScreen(
    id: Long,
    navigateBack: () -> Unit
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

    var lifecycle by rememberSaveable {
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

    val configuration = LocalConfiguration.current
    var orientation by rememberSaveable { mutableIntStateOf(configuration.orientation) }

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { newOrientation ->
                orientation = newOrientation
            }
    }

    var fullscreen by rememberSaveable { mutableStateOf(false) }
    val ratio by rememberSaveable(state.value.video) {
        mutableStateOf(
            state.value.video?.let {
                it.height.toFloat() / it.width
            }
        )
    }

    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val screenModifier = remember(fullscreen) {
        mutableStateOf(
            if (!fullscreen) Modifier.fillMaxWidth(0.8f).verticalScroll(
                scrollState
            ) else Modifier
        )
    }

    BackHandler {
        exitFullScreen(context)
        navigateBack()
    }

    VideoScreenContent(
        modifier = screenModifier.value,
        video = state.value.video,
        lifecycle = lifecycle,
        player = state.value.player,
        landscapeMode = (orientation == ORIENTATION_LANDSCAPE),
        fullscreen = fullscreen,
        onFullscreenClick = {
            fullscreen = !fullscreen
            ratio?.let {
                if (fullscreen) {
                    val newOrientation = if (it > 1f) ORIENTATION_PORTRAIT
                    else ORIENTATION_LANDSCAPE
                    orientation = newOrientation
                    enterFullScreen(context, newOrientation)
                } else exitFullScreen(context)
            }
        }
    )
}

@Composable
fun VideoScreenContent(
    modifier: Modifier = Modifier,
    video: VideoUiModel?,
    lifecycle: Lifecycle.Event,
    landscapeMode: Boolean,
    fullscreen: Boolean,
    onFullscreenClick: () -> Unit,
    player: Player,
) {
    Column(
        modifier = modifier
    ) {
        video?.let {
            Player(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                player = player,
                video = it,
                lifecycle = lifecycle,
                landscapeMode = landscapeMode,
                fullscreen = fullscreen,
                onFullscreenClick = onFullscreenClick
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

@OptIn(UnstableApi::class)
@Composable
fun Player(
    modifier: Modifier = Modifier,
    player: Player,
    video: VideoUiModel,
    lifecycle: Lifecycle.Event,
    landscapeMode: Boolean,
    fullscreen: Boolean,
    onFullscreenClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    var playerModifier = modifier

    if (!fullscreen) {
        playerModifier = if (landscapeMode)
            playerModifier.height(configuration.screenHeightDp.dp * 0.75f)
        else
            playerModifier.width(configuration.screenWidthDp.dp * 0.85f)
        playerModifier =
            playerModifier.aspectRatio(video.width.toFloat() / video.height)
    } else playerModifier = playerModifier.height(configuration.screenHeightDp.dp)
        .width(configuration.screenWidthDp.dp)
        .aspectRatio(video.width.toFloat() / video.height)

    AndroidView(
        factory = { context ->
            (LayoutInflater.from(context).inflate(
                R.layout.player_surface_layout,
                null
            ) as PlayerView).also {
                it.player = player
                it.setFullscreenButtonState(fullscreen)
                it.setFullscreenButtonClickListener {
                    onFullscreenClick()
                }
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
        modifier = playerModifier
    )
}

@SuppressLint("SourceLockedOrientationActivity")
private fun enterFullScreen(context: Context, orientation: Int) {
    val activity = context as Activity
    if (orientation == ORIENTATION_PORTRAIT)
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    else activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    activity.actionBar?.hide()
}

private fun exitFullScreen(context: Context) {
    val activity = context as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    activity.actionBar?.show()
}
