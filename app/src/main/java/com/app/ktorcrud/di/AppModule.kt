package com.app.ktorcrud.di

import com.app.ktorcrud.apicall.ApiService
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.datasource.UserDatasource
import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.dsl.module

/**
 * Created by Priyanka.
 */

val appModule = module {
    single { provideApiService() }
    single { provideApiServiceImpl(get()) }
    single { provideDataSource(get()) }
    factory { LoginViewModel(get()) }
}

fun provideDataSource(apiService: ApiServiceImpl) =
    UserDatasource(apiService)

fun provideApiService() = ApiService(ktorHttpClient)
fun provideApiServiceImpl(apiService: ApiService) = ApiServiceImpl(apiService)


