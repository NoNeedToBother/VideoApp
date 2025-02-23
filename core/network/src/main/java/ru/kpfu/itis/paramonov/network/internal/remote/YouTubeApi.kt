package ru.kpfu.itis.paramonov.network.internal.remote

import retrofit2.http.GET
import ru.kpfu.itis.paramonov.network.internal.model.YouTubeVideosResponseModel

internal interface YouTubeApi {

    @GET("videos?chart=mostPopular")
    suspend fun getMostPopularVideos(): YouTubeVideosResponseModel
}