package ru.kpfu.itis.paramonov.network.internal.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.kpfu.itis.paramonov.network.BuildConfig

internal class PexelsVideoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header(QueryParams.AUTHORIZATION_KEY_HEADER, BuildConfig.pexelsApiKey)
            .build()

        return chain.proceed(newRequest)
    }
}

internal object QueryParams {
    const val AUTHORIZATION_KEY_HEADER = "Authorization"
}
