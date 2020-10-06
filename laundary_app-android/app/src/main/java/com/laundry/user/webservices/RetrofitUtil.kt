package com.laundry.user.webservice


import com.google.gson.GsonBuilder
import com.laundry.user.Constants

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


import java.util.concurrent.TimeUnit

object RetrofitUtil

{

    fun createBaseApiService(
        baseUrl: String = Constants.BASE_URL,
        connectTimeoutInSec: Long = 10,
        readTimeoutInSec: Long = 30,
        writeTimeoutInSec: Long = 60
    ): ApiInterface {
        return getRetrofitClient(getOkhttpClientBuilder(
                connectTimeoutInSec,
                readTimeoutInSec,
                writeTimeoutInSec
        ), baseUrl)
                .create(ApiInterface::class.java)
    }

    fun getRetrofitClient(okHttpClientBuilder: OkHttpClient.Builder, baseUrl: String) = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .baseUrl(baseUrl)
            .build()

    fun getOkhttpClientBuilder(connectTimeoutInSec: Long, readTimeoutInSec: Long, writeTimeoutInSec: Long): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.connectTimeout(connectTimeoutInSec, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(readTimeoutInSec, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(writeTimeoutInSec, TimeUnit.SECONDS)
        return okHttpClientBuilder
    }
}
