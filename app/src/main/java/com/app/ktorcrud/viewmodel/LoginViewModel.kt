package com.app.ktorcrud.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.utils.AllEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Priyanka.
 */
class LoginViewModel(
    private val apiServiceImpl: ApiServiceImpl
) :
    ViewModel() {
    val users = MutableStateFlow<AllEvents>(AllEvents.Nothing)

    fun getUsers() {
        viewModelScope.launch {
            users.value = AllEvents.Loading(true)
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