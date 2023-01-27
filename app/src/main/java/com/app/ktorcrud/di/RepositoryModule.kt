package com.app.ktorcrud.di

import com.app.ktorcrud.apicall.ApiService
import com.app.ktorcrud.repository.ApiServiceRepository
import com.app.ktorcrud.repository.ApiServiceRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideApiServiceRepository(api:ApiService):ApiServiceRepository {
        return ApiServiceRepositoryImpl(api)
    }
    single { provideApiServiceRepository(get()) }

}