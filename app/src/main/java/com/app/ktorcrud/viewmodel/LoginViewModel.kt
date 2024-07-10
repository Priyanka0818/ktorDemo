package com.app.ktorcrud.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ktorcrud.R
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.utils.AllEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Priyanka.
 */
class LoginViewModel(private val apiServiceImpl: ApiServiceImpl) :
    ViewModel() {

    val isNetworkAvailable = MutableLiveData<Boolean?>()
    var progress = ObservableField<Int>()
    val users = MutableStateFlow<AllEvents>(AllEvents.Loading)

    fun getUsers() {
        viewModelScope.launch {
            when {
                !isNetworkAvailable.value!! -> {
                    users.value = AllEvents.Loading
                    users.value = AllEvents.StringResource(R.string.noInternet)
                }

                else -> {
                    apiServiceImpl.getUserList(1).either({
                        users.value = AllEvents.DynamicError(it)
                        it
                    }) {
                        users.value = AllEvents.Success(it.data)
                        it
                    }
                }
            }
        }
    }
}