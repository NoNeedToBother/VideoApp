package ru.kpfu.itis.paramonov.videos.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.koin.compose.koinInject
import ru.kpfu.itis.paramonov.videos.R
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoStatisticsUiModel
import ru.kpfu.itis.paramonov.videos.presentation.model.VideoUiModel
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenIntent
import ru.kpfu.itis.paramonov.videos.presentation.mvi.videos.VideosScreenSideEffect
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideosViewModel
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlin.time.Duration

private const val VIDEO_LIMIT = 5
private const val HOUR_PASSED_TO_UPDATE_VIDEOS = 4

private fun getAfterDate(): Date {
    val now = Date.from(Instant.now())
    val after = now.time - HOUR_PASSED_TO_UPDATE_VIDEOS * 60 * 60 * 1000
    return Date.from(Instant.ofEpochMilli(after))
}

@Composable
fun VideosScreen(
    goToVideoScreen: (String) -> Unit
) {
    val viewModel = koinInject<VideosViewModel>()

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    LaunchedEffect(Unit) {
        viewModel.onIntent(VideosScreenIntent.GetMostPopularVideos(
            limit = VIDEO_LIMIT,
            after = getAfterDate()
        ))

        effect.collect {
            when(it) {
                is VideosScreenSideEffect.ShowError -> {}
            }
        }
    }

    VideosScreenContent(
        videos = state.value.videos,
        isRefreshing = state.value.isRefreshing,
        onRefresh = { viewModel.onIntent(VideosScreenIntent.GetMostPopularVideos(
            limit = VIDEO_LIMIT,
            after = getAfterDate()
        )) },
        onVideoClick = { video -> goToVideoScreen(video) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun VideosScreenContent(
    modifier: Modifier = Modifier,
    videos: List<VideoUiModel>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onVideoClick: (String) -> Unit
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
                VideoItem(
                    video = it,
                    onClick = onVideoClick
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun VideoItem(
    modifier: Modifier = Modifier,
    video: VideoUiModel,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = modifier.clickable {
            onClick(video.id)
        }.wrapContentHeight()
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
            painter = rememberAsyncImagePainter(video.standardThumbnailUrl),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
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
        ViewAndLikeSection(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            statistics = video.statistics
        )
    }
}

@Composable
fun Time(
    modifier: Modifier = Modifier,
    time: Duration,
) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        text = time.toComponents { hours, minutes, seconds, _ ->
            String.format(Locale.ROOT, "%02dh:%02dm:%02ds", hours, minutes, seconds)
        }
    )
}

@Composable
fun ViewAndLikeSection(
    modifier: Modifier = Modifier,
    statistics: VideoStatisticsUiModel
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.views),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = statistics.viewCount.toString(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.likes),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = statistics.likeCount.toString(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
