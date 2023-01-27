package com.app.ktorcrud.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.ktorcrud.R
import com.app.ktorcrud.apicall.ApiServiceImpl2
import com.app.ktorcrud.datasource.PAGE_SIZE
import com.app.ktorcrud.datasource.UserDatasource
import com.app.ktorcrud.repository.ApiServiceRepository
import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.FileUploadResult
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.utils.validateEmail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by Priyanka.
 */
class LoginViewModel(val apiServiceRepository: ApiServiceRepository) :
    ViewModel() {

    val isNetworkAvailable = MutableLiveData<Boolean?>()
    var email = ObservableField<String>()
    var progress = ObservableField<Int>()
    var password = ObservableField<String>()
    var name = ObservableField<String>()
    var job = ObservableField<String>()
    internal val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()
    var filePath = ""


    fun login() {

        val emailString = email.get()
        val passString = password.get()
        viewModelScope.launch {
            when {
                !isNetworkAvailable.value!! -> {
                    eventsChannel.send(AllEvents.StringResource(R.string.noInternet))
                }
                emailString.isNullOrBlank() -> {
                    eventsChannel.send(
                        AllEvents.StringResource(R.string.enter_email, null)
                    )
                }
                emailString.validateEmail() -> {
                    eventsChannel.send(AllEvents.StringResource(R.string.error_email_invalid))
                }
                passString.isNullOrBlank() -> {
                    eventsChannel.send(AllEvents.StringResource(R.string.error_password_empty))
                }
                else -> {
                    eventsChannel.send(AllEvents.Loading(true))
                    apiServiceRepository.login(LoginRequestModel(emailString, passString))
                        .either(
                            {
                                eventsChannel.send(AllEvents.Loading(false))
                                eventsChannel.send(AllEvents.DynamicError(it))
                            }
                        ) {
                            eventsChannel.send(AllEvents.Loading(false))
                            eventsChannel.send(AllEvents.SuccessBool(true, 1))
                        }
                }
            }
        }
    }
}