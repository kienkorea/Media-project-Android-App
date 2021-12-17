package com.example.up_down_android.module

import com.example.up_down_android.BuildConfig
import com.example.up_down_android.data.UpdownServiceUtil
import com.example.up_down_android.util.PrefUtil
import com.example.up_down_android.util.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkMoudle = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttp = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(TokenInterceptor(PrefUtil(androidContext())))
            .build()

        Retrofit.Builder()
            .baseUrl("http://5c4c-182-227-161-146.ngrok.io/")
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(UpdownServiceUtil::class.java)
    }
}