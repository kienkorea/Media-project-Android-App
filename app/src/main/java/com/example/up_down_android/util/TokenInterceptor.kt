package com.example.up_down_android.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TokenInterceptor(val prefUtil: PrefUtil) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
        try {
            if (prefUtil.getUserAccessToken() != null) {
                builder.addHeader(
                    "Authorization",
                    "Bearer ${prefUtil.getUserAccessToken()}"
                )
            }
        } catch (ignored: Exception) {
        }
        val request: Request = builder.build()
        return chain.proceed(request)
    }
}