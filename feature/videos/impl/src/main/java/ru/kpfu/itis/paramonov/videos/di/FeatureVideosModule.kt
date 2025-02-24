package ru.kpfu.itis.paramonov.videos.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kpfu.itis.paramonov.videos.api.usecase.GetMostPopularYouTubeVideosUseCase
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideosUseCase
import ru.kpfu.itis.paramonov.videos.api.usecase.GetSavedVideoUseCase
import ru.kpfu.itis.paramonov.videos.api.usecase.SaveVideosUseCase
import ru.kpfu.itis.paramonov.videos.domain.mapper.VideoUiModelMapper
import ru.kpfu.itis.paramonov.videos.domain.usecase.GetMostPopularVideosUseCaseImpl
import ru.kpfu.itis.paramonov.videos.domain.usecase.GetSavedVideosUseCaseImpl
import ru.kpfu.itis.paramonov.videos.domain.usecase.GetSavedVideoUseCaseImpl
import ru.kpfu.itis.paramonov.videos.domain.usecase.SaveVideosUseCaseImpl
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideosViewModel
import ru.kpfu.itis.paramonov.videos.presentation.viewmodel.VideoViewModel

val featureVideosModule = module {
    factoryOf(::VideoUiModelMapper)

    factoryOf(::GetMostPopularVideosUseCaseImpl) bind GetMostPopularYouTubeVideosUseCase::class
    factoryOf(::GetSavedVideoUseCaseImpl) bind GetSavedVideoUseCase::class
    factoryOf(::GetSavedVideosUseCaseImpl) bind GetSavedVideosUseCase::class
    factoryOf(::SaveVideosUseCaseImpl) bind SaveVideosUseCase::class

    viewModelOf(::VideosViewModel)
    viewModelOf(::VideoViewModel)
}