package ru.kpfu.itis.paramonov.network.internal.model

import com.google.gson.annotations.SerializedName

internal data class YouTubeVideosResponseModel(
    @SerializedName("items") val videos: List<YouTubeVideoResponseModel>,
)

internal data class YouTubeVideoResponseModel(
    @SerializedName("id") val id: String,
    @SerializedName("snippet") val snippet: YouTubeVideoResponseSnippetModel,
    @SerializedName("contentDetails") val details: YouTubeVideoResponseContentDetailsModel,
    @SerializedName("statistics") val statistics: YouTubeVideoResponseStatisticsModel,
)

internal data class YouTubeVideoResponseSnippetModel(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnails") val thumbnails: YouTubeVideoResponseThumbnailsModel,
)

internal data class YouTubeVideoResponseContentDetailsModel(
    @SerializedName("duration") val duration: String,
)

internal data class YouTubeVideoResponseThumbnailsModel(
    @SerializedName("default") val defaultThumbnail: YouTubeVideoResponseThumbnailModel,
    @SerializedName("standard") val standardThumbnail: YouTubeVideoResponseThumbnailModel,
)

internal data class YouTubeVideoResponseThumbnailModel(
    @SerializedName("url") val url: String,
)

internal data class YouTubeVideoResponseStatisticsModel(
    @SerializedName("viewCount") val viewCount: Int,
    @SerializedName("likeCount") val likeCount: Int
)
