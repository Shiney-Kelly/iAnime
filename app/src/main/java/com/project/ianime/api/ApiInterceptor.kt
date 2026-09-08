package com.project.ianime.api

import com.project.ianime.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class ApiInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val acceptLanguage = Locale.getDefault().language

        val updatedRequest = request.newBuilder()
            .addHeader(HEADER_ACCEPT_LANGUAGE, acceptLanguage)
            .addHeader(HEADER_API_KEY, BuildConfig.API_KEY)
            .build()
        return chain.proceed(updatedRequest)
    }

    companion object {
        const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
        const val HEADER_API_KEY = "Authorization"
    }

}