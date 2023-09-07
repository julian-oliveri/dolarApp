package com.example.dolarApp.mvvm.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val URL_DOLAR_OPEN = "https://www.dolarsi.com/"

    val webService by lazy {
        Retrofit.Builder().client(getOkHttpClient()).baseUrl(URL_DOLAR_OPEN)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WebService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }


        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun provideWebService(retrofit: Retrofit): WebService {
        return retrofit.create(WebService::class.java)
    }
}