package com.app.ktorcrud.application

import android.app.Application
import com.app.ktorcrud.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Priyanka.
 */
class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(appModule, apiModule, networkModule, repositoryModule, viewModelModule)
        }
    }
}