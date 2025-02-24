package ru.kpfu.itis.paramonov.videos.api.usecase

import ru.kpfu.itis.paramonov.videos.api.model.VideoModel

interface GetSavedVideoUseCase {

    suspend operator fun invoke(id: Long): VideoModel

}