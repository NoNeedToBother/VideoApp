package ru.kpfu.itis.paramonov.network.internal.mapper

import ru.kpfu.itis.paramonov.network.external.model.PexelsVideoModel
import ru.kpfu.itis.paramonov.network.internal.model.PexelsVideoFileResponseModel
import ru.kpfu.itis.paramonov.network.internal.model.PexelsVideosResponseModel

internal class PexelsVideoModelMapper {

    fun map(videos: PexelsVideosResponseModel): List<PexelsVideoModel> {
        return videos.videos.map { video ->
            val betterVideo = getBetterVideo(video.files)
            PexelsVideoModel(
                id = video.id,
                title = extractVideoTitle(video.id, video.url),
                duration = video.durationSeconds,
                videoUrl = betterVideo.url,
                height = betterVideo.height,
                width = betterVideo.width,
                thumbnailUrl = video.thumbnails.first().url,
            )
        }
    }

    private fun extractVideoTitle(id: Long, url: String): String {
        var isNextName = false
        var result = ""
        url.split("/").forEach {
            if (isNextName) {
                isNextName = false
                result = it.split("-")
                    .filter { word -> word != id.toString() }
                    .joinToString(" ")
            }

            if (it == "video")
                isNextName = true
        }

        return result.ifEmpty { "No title" }
    }

    private fun getBetterVideo(
        videos: List<PexelsVideoFileResponseModel>
    ): PexelsVideoFileResponseModel {
        val isHeightBigger = videos.first().height > videos.first().height

        val optimal = videos.filter {
            if (isHeightBigger) it.width in 360..900
            else it.height in 360..900
        }.maxByOrNull {
            if (isHeightBigger) it.width
            else it.height
        }
        optimal?.let {
            return it
        }

        val higherResolution = videos.filter {
            if (isHeightBigger) it.width > 900
            else it.height > 900
        }.minByOrNull {
            if (isHeightBigger) it.width
            else it.height
        }
        higherResolution?.let {
            return it
        }

        val lowerResolution = videos.filter {
            if (isHeightBigger) it.width < 360
            else it.height < 360
        }.minByOrNull {
            if (isHeightBigger) it.width
            else it.height
        }
        lowerResolution?.let {
            return it
        }

        return videos.first()
    }
}