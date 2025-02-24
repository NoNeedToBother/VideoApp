package ru.kpfu.itis.paramonov.network.internal.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.kpfu.itis.paramonov.network.internal.model.PexelsVideosResponseModel

internal interface PexelsApi {

    @GET("popular")
    suspend fun getMostPopularVideos(@Query("per_page") limit: Int): PexelsVideosResponseModel
}