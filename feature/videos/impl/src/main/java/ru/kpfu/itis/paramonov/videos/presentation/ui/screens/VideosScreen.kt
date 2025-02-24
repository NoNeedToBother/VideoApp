package ru.kpfu.itis.paramonov.videos.presentation.ui.screens

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.koin.compose.koinInject
import ru.kpfu.itis.paramonov.videos.R
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.ui.components.ErrorDialog
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideosViewModel
import java.time.Instant
import java.util.Date

private const val VIDEO_LIMIT = 5
private const val HOUR_PASSED_TO_UPDATE_VIDEOS = 4

private fun getAfterDate(): Date {
    val now = Date.from(Instant.now())
    val after = now.time - HOUR_PASSED_TO_UPDATE_VIDEOS * 60 * 60 * 1000
    return Date.from(Instant.ofEpochMilli(after))
}

@Composable
fun VideosScreen(
    goToVideoScreen: (Long) -> Unit
) {
    val viewModel = koinInject<VideosViewModel>()

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.onIntent(VideosScreenIntent.GetMostPopularVideos(
            limit = VIDEO_LIMIT,
            after = getAfterDate()
        ))

        effect.collect {
            when(it) {
                is VideosScreenSideEffect.ShowError -> {
                    error = it.title to it.message
                }
            }
        }
    }

    val configuration = LocalConfiguration.current
    var orientation by remember { mutableStateOf(configuration.orientation) }

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { newOrientation ->
                orientation = newOrientation
            }
    }

    if (orientation == ORIENTATION_PORTRAIT) {
        VideosScreenPortraitContent(
            videos = state.value.videos,
            isRefreshing = state.value.isRefreshing,
            onRefresh = {
                viewModel.onIntent(
                    VideosScreenIntent.GetMostPopularVideos(
                        limit = VIDEO_LIMIT,
                        after = getAfterDate()
                    )
                )
            },
            onVideoClick = { video -> goToVideoScreen(video) }
        )
    } else {
        VideosScreenLandscapeContent(
            videos = state.value.videos,
            isRefreshing = state.value.isRefreshing,
            onRefresh = {
                viewModel.onIntent(
                    VideosScreenIntent.GetMostPopularVideos(
                        limit = VIDEO_LIMIT,
                        after = getAfterDate()
                    )
                )
            },
            onVideoClick = { video -> goToVideoScreen(video) }
        )
    }

    Box {
        error?.let {
            ErrorDialog(
                onDismiss = { error = null },
                title = it.first,
                text = it.second
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun VideosScreenPortraitContent(
    modifier: Modifier = Modifier,
    videos: List<VideoUiModel>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onVideoClick: (Long) -> Unit
) {
    PullToRefreshBox(
        modifier = modifier.padding(top = 32.dp),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            items(videos) {
                VideoPortraitItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    video = it,
                    onClick = onVideoClick
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun VideosScreenLandscapeContent(
    modifier: Modifier = Modifier,
    videos: List<VideoUiModel>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onVideoClick: (Long) -> Unit
) {
    PullToRefreshBox(
        modifier = modifier.padding(top = 32.dp),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            items(videos) {
                VideoLandscapeItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    video = it,
                    onClick = onVideoClick
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun VideoPortraitItem(
    modifier: Modifier = Modifier,
    video: VideoUiModel,
    onClick: (Long) -> Unit,
) {
    val ratio by remember(video) { mutableFloatStateOf(video.height.toFloat() / video.width) }
    Column(
        modifier = modifier.clickable {
            onClick(video.id)
        }.wrapContentHeight()
            .fillMaxWidth()
    ) {
        VideoItemContent(
            displayAsColumn = ratio < 1f,
            video = video,
        )
    }
}

@Composable
fun VideoLandscapeItem(
    modifier: Modifier = Modifier,
    video: VideoUiModel,
    onClick: (Long) -> Unit,
) {
    Column(
        modifier = modifier.clickable {
            onClick(video.id)
        }.wrapContentHeight()
            .fillMaxWidth()
    ) {
        VideoItemContent(
            displayAsColumn = false,
            video = video,
        )
    }
}

@Composable
fun VideoItemContent(
    modifier: Modifier = Modifier,
    displayAsColumn: Boolean,
    video: VideoUiModel,
) {
    if (displayAsColumn) {
        Column(
            modifier = modifier
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
                painter = rememberAsyncImagePainter(video.thumbnailUrl),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            TitleWithTime(
                modifier = Modifier.fillMaxWidth(),
                video = video
            )
        }
    } else {
        Row(
            modifier = modifier
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(0.6f),
                painter = rememberAsyncImagePainter(video.thumbnailUrl),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            TitleWithTime(
                modifier = Modifier.fillMaxWidth(),
                video = video
            )
        }
    }
}

@Composable
fun TitleWithTime(
    modifier: Modifier = Modifier,
    video: VideoUiModel
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = video.title,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Time(
            modifier = Modifier.fillMaxWidth(),
            time = video.duration
        )
    }
}

@Composable
fun Time(
    modifier: Modifier = Modifier,
    time: Int,
) {
    val seconds = remember(time) { time % 60 }
    val minutes = remember(time) { time % 3600 / 60 }
    val hours = remember(time) { time / 3600 }
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        text = stringResource(R.string.video_duration, hours, minutes, seconds)
    )
}
