package com.app.ktorcrud.apicall

import com.app.ktorcrud.utils.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Priyanka.
 */

object APIClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(LoggingInterceptor())
            httpClient.readTimeout(30, TimeUnit.MINUTES)
            httpClient.connectTimeout(30, TimeUnit.MINUTES)
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.upload.io/v2/accounts/FW25b1r/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }
        return retrofit
    }
}