package com.app.ktorcrud.di

import android.app.Application
import com.app.ktorcrud.apicall.ApiService
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.dsl.module

/**
 * Created by Priyanka.
 */

val appModule = module {
    single { provideApiService() }
    single { provideApiServiceImpl(get()) }
    factory { LoginViewModel(get()) }
}


fun provideApiService() = ApiService(ktorHttpClient)
fun provideApiServiceImpl(apiService: ApiService) = ApiServiceImpl(apiService)
fun provideViewModel(apiService: ApiServiceImpl, context: Application) =
    LoginViewModel(apiService)

