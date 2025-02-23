package ru.kpfu.itis.paramonov.videoapp.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val commonModule = module {
    factory<CoroutineDispatcher> { Dispatchers.IO }
}