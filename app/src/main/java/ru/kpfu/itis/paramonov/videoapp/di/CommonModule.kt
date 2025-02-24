package ru.kpfu.itis.paramonov.videoapp.di

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import ru.kpfu.itis.paramonov.core.resources.ResourceManager

val commonModule = module {
    factory<CoroutineDispatcher> { Dispatchers.IO }

    factory<ResourceManager> {
        val context: Context = get()
        object : ResourceManager {
            override fun getString(stringId: Int): String = context.getString(stringId)

            override fun getString(stringId: Int, vararg args: Any?): String =
                context.getString(stringId, *args)

        }
    }
}