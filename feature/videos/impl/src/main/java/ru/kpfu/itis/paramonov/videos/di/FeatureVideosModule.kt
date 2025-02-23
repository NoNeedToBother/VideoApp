package ru.kpfu.itis.paramonov.videos.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kpfu.itis.paramonov.videos.api.usecase.GetMostPopularYouTubeVideosUseCase
import ru.kpfu.itis.paramonov.videos.domain.mapper.VideoUiModelMapper
import ru.kpfu.itis.paramonov.videos.domain.usecase.GetMostPopularVideosUseCaseImpl
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideosViewModel

val featureVideosModule = module {
    factoryOf(::VideoUiModelMapper)

    factoryOf(::GetMostPopularVideosUseCaseImpl) bind GetMostPopularYouTubeVideosUseCase::class

    viewModelOf(::VideosViewModel)
}