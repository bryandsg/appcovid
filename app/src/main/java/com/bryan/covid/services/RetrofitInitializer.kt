package com.bryan.covid.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInitializer {

    companion object {
        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                chain.proceed(newRequest.build())
            }
            .addInterceptor(HttpLoggingInterceptor().also { it ->
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        }
    }

    private val covidRetrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.covid19api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCovidService() :CovidService {
        return covidRetrofit.create(CovidService::class.java)
    }
}