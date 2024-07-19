package com.app.ktorcrud.di

import android.content.Context
import com.app.ktorcrud.apicall.ApiService
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.utils.NetworkConnection
import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.dsl.module

/**
 * Created by Priyanka.
 */

val appModule = module {
    single { provideApiService() }
    single { provideApiServiceImpl(get()) }
    single { provideNetworkConnection(get()) }
    factory { LoginViewModel(get()) }
}

fun provideApiService() = ApiService(ktorHttpClient)
fun provideApiServiceImpl(apiService: ApiService) = ApiServiceImpl(apiService)
fun provideNetworkConnection(context: Context) = NetworkConnection(context)


