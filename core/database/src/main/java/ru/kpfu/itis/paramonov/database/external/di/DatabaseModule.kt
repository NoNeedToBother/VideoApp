package ru.kpfu.itis.paramonov.database.external.di

import android.content.Context
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kpfu.itis.paramonov.database.external.repository.VideoRepository
import ru.kpfu.itis.paramonov.database.internal.database.VideoDatabase
import ru.kpfu.itis.paramonov.database.internal.mapper.VideoMapper
import ru.kpfu.itis.paramonov.database.internal.repository.VideoRepositoryImpl

val databaseModule = module {
    factory<String>(named("video_database_tag")) {
        "video_database"
    }

    single<VideoDatabase> {
        val name: String = get(named("video_database_tag"))
        val context: Context = get()
        VideoDatabase.init(context, name)
    }

    factoryOf(::VideoMapper)
    factoryOf(::VideoRepositoryImpl) bind VideoRepository::class
}