package ru.kpfu.itis.paramonov.network.internal.model

import com.google.gson.annotations.SerializedName

internal data class PexelsVideosResponseModel(
    @SerializedName("videos") val videos: List<PexelsVideoResponseModel>,
)

internal data class PexelsVideoResponseModel(
    @SerializedName("id") val id: Long,
    @SerializedName("url") val url: String,
    @SerializedName("duration") val durationSeconds: Int,
    @SerializedName("video_files") val files: List<PexelsVideoFileResponseModel>,
    @SerializedName("video_pictures") val thumbnails: List<PexelsVideoPictureResponseModel>
)

internal data class PexelsVideoFileResponseModel(
    @SerializedName("link") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
)

internal data class PexelsVideoPictureResponseModel(
    @SerializedName("picture") val url: String,
)
