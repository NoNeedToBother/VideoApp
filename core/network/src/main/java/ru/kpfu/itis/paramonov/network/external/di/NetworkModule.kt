package ru.kpfu.itis.paramonov.network.external.di

import okhttp3.OkHttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.paramonov.network.external.repository.PexelsVideoRepository
import ru.kpfu.itis.paramonov.network.internal.remote.PexelsApi
import ru.kpfu.itis.paramonov.network.internal.repository.PexelsVideoRepositoryImpl
import ru.kpfu.itis.paramonov.network.internal.interceptor.PexelsVideoInterceptor
import ru.kpfu.itis.paramonov.network.internal.mapper.PexelsVideoModelMapper
import ru.kpfu.itis.paramonov.network.internal.utils.PEXELS_API_BASE_URL

val networkModule = module {
    single<OkHttpClient> {
        val interceptor: PexelsVideoInterceptor = get()
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(interceptor).build()
    }
    single<PexelsApi> {
        val okHttpClient: OkHttpClient = get()
        val builder = Retrofit.Builder()
            .baseUrl(PEXELS_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        builder.create(PexelsApi::class.java)
    }

    factoryOf(::PexelsVideoInterceptor)
    factoryOf(::PexelsVideoModelMapper)

    factoryOf(::PexelsVideoRepositoryImpl) bind PexelsVideoRepository::class
}
