package ru.kpfu.itis.paramonov.network.external.repository

import ru.kpfu.itis.paramonov.network.external.model.PexelsVideoModel

interface PexelsVideoRepository {

    suspend fun getMostPopularVideos(limit: Int): List<PexelsVideoModel>

}