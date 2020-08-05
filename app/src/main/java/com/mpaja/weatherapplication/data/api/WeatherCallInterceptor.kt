package com.mpaja.weatherapplication.data.api

import android.content.Context
import com.mpaja.weatherapplication.R
import okhttp3.Interceptor
import okhttp3.Response

class WeatherCallInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request();
        val url =
            request.url.newBuilder().addQueryParameter("appid", context.getString(R.string.api_key))
                .build();
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}