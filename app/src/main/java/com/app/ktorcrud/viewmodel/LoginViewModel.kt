package com.app.ktorcrud.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.ktorcrud.R
import com.app.ktorcrud.datasource.PAGE_SIZE
import com.app.ktorcrud.datasource.UserDatasource
import com.app.ktorcrud.repository.ApiServiceRepository
import com.app.ktorcrud.request.LoginRequestModel
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
    var password = ObservableField<String>()
    var name = ObservableField<String>()
    internal val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()


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

    private val pagedUserListLiveData =
        Pager(PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = {
            UserDatasource(apiServiceRepository) {
                viewModelScope.launch {
                    eventsChannel.send(AllEvents.DynamicError(it.error))
                }
            }
        }).flow


    fun getUsers() {
        viewModelScope.launch {
            when {
                !isNetworkAvailable.value!! -> {
                    eventsChannel.send(AllEvents.StringResource(R.string.noInternet))
                }
                else -> {
                    apiServiceRepository.getUserList(1).either({
                        eventsChannel.send(AllEvents.DynamicError(it))
                    }, {
                        eventsChannel.send(AllEvents.SuccessBool(true, 2))
                        eventsChannel.send(AllEvents.Success(it.data))
                    })


                    /*  eventsChannel.send(AllEvents.Loading(true))
                      pagedUserListLiveData.collect {
                          viewModelScope.launch {
                              eventsChannel.send(AllEvents.Loading(false))
  //                            userListResponse.postValue(it)
                              eventsChannel.send(AllEvents.SuccessBool(true, 2))
                              eventsChannel.send(AllEvents.Success(it))
                          }
                      }*/
                }
            }
        }
    }

    fun uploadImage(file: File) {
        viewModelScope.launch {
            apiServiceRepository.uploadImage(file).either({
                eventsChannel.send(AllEvents.DynamicError(it.errors[0].error?.message!!))
            }, {
                eventsChannel.send(AllEvents.SuccessBool(true, 2))
                eventsChannel.send(AllEvents.Success(it))
            })
        }
    }
}