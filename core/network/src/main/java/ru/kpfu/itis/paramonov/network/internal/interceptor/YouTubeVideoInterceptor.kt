package ru.kpfu.itis.paramonov.network.internal.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import ru.kpfu.itis.paramonov.network.BuildConfig
import ru.kpfu.itis.paramonov.network.internal.interceptor.QueryParams.PART_PARAM_VALUES

internal class YouTubeVideoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter(QueryParams.PART_PARAM, PART_PARAM_VALUES)
            .addQueryParameter(QueryParams.REGION_CODE_PARAM, QueryParams.REGION_CODE_PARAM_VALUE)
            .addQueryParameter(QueryParams.API_KEY_PARAM, BuildConfig.youTubeApiKey)
            .build()
        Log.i("AAAAAAAAA", newUrl.toString())

        val requestBuilder = chain.request().newBuilder().url(newUrl)

        return chain.proceed(requestBuilder.build())
    }
}

internal object QueryParams {
    const val PART_PARAM = "part"
    const val PART_PARAM_VALUES = "snippet,contentDetails,statistics"

    const val REGION_CODE_PARAM = "regionCode"
    const val REGION_CODE_PARAM_VALUE = "RU"

    const val API_KEY_PARAM = "key"
}
