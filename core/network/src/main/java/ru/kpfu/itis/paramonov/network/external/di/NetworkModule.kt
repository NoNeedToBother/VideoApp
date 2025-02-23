package ru.kpfu.itis.paramonov.network.external.di

import okhttp3.OkHttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.paramonov.network.external.repository.YouTubeVideoRepository
import ru.kpfu.itis.paramonov.network.internal.remote.YouTubeApi
import ru.kpfu.itis.paramonov.network.internal.repository.YouTubeVideoRepositoryImpl
import ru.kpfu.itis.paramonov.network.internal.interceptor.YouTubeVideoInterceptor
import ru.kpfu.itis.paramonov.network.internal.mapper.YouTubeVideoModelMapper
import ru.kpfu.itis.paramonov.network.internal.utils.YOUTUBE_API_BASE_URL

val networkModule = module {
    single<OkHttpClient> {
        val interceptor: YouTubeVideoInterceptor = get()
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(interceptor).build()
    }
    single<YouTubeApi> {
        val okHttpClient: OkHttpClient = get()
        val builder = Retrofit.Builder()
            .baseUrl(YOUTUBE_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        builder.create(YouTubeApi::class.java)
    }

    factoryOf(::YouTubeVideoInterceptor)
    factoryOf(::YouTubeVideoModelMapper)

    factoryOf(::YouTubeVideoRepositoryImpl) bind YouTubeVideoRepository::class
}
