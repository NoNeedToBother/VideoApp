package ru.kpfu.itis.paramonov.database.internal.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.paramonov.database.external.model.Video
import ru.kpfu.itis.paramonov.database.external.repository.VideoRepository
import ru.kpfu.itis.paramonov.database.internal.database.VideoDatabase
import ru.kpfu.itis.paramonov.database.internal.mapper.VideoMapper
import java.util.Date

internal class VideoRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val database: VideoDatabase,
    private val mapper: VideoMapper,
): VideoRepository {
    override suspend fun getLatestVideos(limit: Int, after: Date): List<Video> {
        return withContext(dispatcher) {
            database.videoDao().getLatestVideos(
                limit = limit, after = after.time
            ).map { video ->
                mapper.fromEntity(video)
            }
        }
    }

    override suspend fun getLatestVideoByYoutubeId(id: Long): Video {
        return withContext(dispatcher) {
            mapper.fromEntity(
                database.videoDao().getLatestVideoByYoutubeId(id)
            )
        }
    }

    override suspend fun saveVideos(videos: List<Video>) {
        withContext(dispatcher) {
            videos.map { video ->
                mapper.toEntity(video)
            }.let { videos ->
                database.videoDao().saveVideos(videos)
            }
        }
    }
}
