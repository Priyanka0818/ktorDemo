package com.app.ktorcrud.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.app.ktorcrud.R
import com.app.ktorcrud.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appModule = module {
    single {
        val sharedPreferences = androidApplication().getSharedPreferences(
            androidApplication().getString(R.string.app_name),
            android.content.Context.MODE_PRIVATE
        )
        getSharedPrefs(androidApplication())
        provideSharedPreferenceUtils(sharedPreferences)
    }
}


fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        androidApplication.getString(R.string.app_name),
        android.content.Context.MODE_PRIVATE
    )
}

fun provideSharedPreference(context: Context) =
    context.getSharedPreferences(
        context.getString(R.string.app_name),
        AppCompatActivity.MODE_PRIVATE
    )


fun provideSharedPreferenceUtils(sharedPreferences: SharedPreferences) =
    SharedPreferenceUtils(sharedPreferences)