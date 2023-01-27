package com.app.ktorcrud.di

import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        LoginViewModel(get())
    }

}