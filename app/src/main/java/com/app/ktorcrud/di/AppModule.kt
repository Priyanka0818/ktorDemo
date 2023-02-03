package com.app.ktorcrud.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.app.ktorcrud.utils.SharedPreferenceUtils
import org.koin.dsl.module


val appModule = module {
    single {
        provideSharedPreference(get())
        provideSharedPreferenceUtils(get())
    }
}


fun provideSharedPreference(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)


fun provideSharedPreferenceUtils(sharedPreferences: SharedPreferences) =
    SharedPreferenceUtils(sharedPreferences)