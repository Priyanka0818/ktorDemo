package com.app.ktorcrud.di

import com.app.ktorcrud.apicall.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    single { provideApiService(get()) }

}