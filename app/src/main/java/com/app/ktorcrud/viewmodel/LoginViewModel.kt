package com.app.ktorcrud.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.ktorcrud.R
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.datasource.PAGE_SIZE
import com.app.ktorcrud.datasource.UserDatasource
import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.response.FileUploadResult
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.utils.validateEmail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by Priyanka.
 */
class LoginViewModel(val apiServiceImpl: ApiServiceImpl) :
    ViewModel() {

    val isNetworkAvailable = MutableLiveData<Boolean?>()
    var email = ObservableField<String>()
    var progress = ObservableField<Int>()
    var password = ObservableField<String>()
    var name = ObservableField<String>()
    var job = ObservableField<String>()
    internal val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    private val loginResponse get() = _loginResponse
    var filePath = ""
    private val _userListResponse = MutableLiveData<ArrayList<Data>?>()
    val userListResponse get() = _userListResponse

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
                    apiServiceImpl.login(LoginRequestModel(emailString, passString))
                        .either(
                            {
                                eventsChannel.send(AllEvents.Loading(false))
                                eventsChannel.send(AllEvents.DynamicError(it))
                            }
                        ) {
                            eventsChannel.send(AllEvents.Loading(false))
                            loginResponse.postValue(it)
                            eventsChannel.send(AllEvents.SuccessBool(true, 1))
                        }
                }
            }
        }
    }

    private val pagedUserListLiveData =
        Pager(PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = {
            UserDatasource(apiServiceImpl) {
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
                    eventsChannel.send(AllEvents.Loading(true))
                    pagedUserListLiveData.collect {
                        viewModelScope.launch {
                            eventsChannel.send(AllEvents.Loading(false))
//                            userListResponse.postValue(it)
                            eventsChannel.send(AllEvents.SuccessBool(true, 2))
                            eventsChannel.send(AllEvents.Success(it))
                        }
                    }
                }
            }
        }
    }


    fun updateUser() {
        name.set("michel")
        job.set("potrait")
        val nameString = name.get()
        val jobString = job.get()

        viewModelScope.launch {
            when {
                !isNetworkAvailable.value!! -> {
                    eventsChannel.send(AllEvents.StringResource(R.string.noInternet))
                }
                else -> {
                    apiServiceImpl.updateUser(2, UpdateUserRequest(nameString!!, jobString!!))
                        .either({
                            eventsChannel.send(AllEvents.DynamicError(it))
                        }) {
                            eventsChannel.send(AllEvents.SuccessBool(true, 3))
                            eventsChannel.send(AllEvents.Success(it))
                        }
                }
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            when {
                !isNetworkAvailable.value!! -> {
                    eventsChannel.send(AllEvents.StringResource(R.string.noInternet))
                }
                else -> {
                    apiServiceImpl.deleteUser(2)
                        .either({
                            eventsChannel.send(AllEvents.DynamicError(it))
                        }) {
                            eventsChannel.send(AllEvents.SuccessBool(true, 3))
                            eventsChannel.send(AllEvents.Success(it))
                        }
                }
            }
        }
    }

    fun uploadImage(file: File) {
        viewModelScope.launch {
            apiServiceImpl.uploadImage(file).collect {
                when (it) {
                    is FileUploadResult.Progress -> {
                        Log.e("TAG", "uploadImage: ${it.progress}")
                        progress.set(it.progress)
//                        eventsChannel.send(AllEvents.Progress(it.progress))
                    }
                    is FileUploadResult.Success -> {
                        Log.e("TAG", "uploadImage: ${it}")
                        eventsChannel.send(AllEvents.Success(it.data))
                    }
                    else -> {
                        it as FileUploadResult.Error
                        eventsChannel.send(AllEvents.DynamicError(it.message))
                    }
                }
            }
        }
    }

    fun downloadImage() {
        viewModelScope.launch {
            apiServiceImpl.downloadImage(filePath).collect {
                when (it) {
                    is FileUploadResult.Progress -> {
                        Log.e("TAG", "uploadImage: ${it.progress}")
                        progress.set(it.progress)
//                        eventsChannel.send(AllEvents.Progress(it.progress))
                    }
                    is FileUploadResult.Success -> {
                        Log.e("TAG", "uploadImage: ${it}")
                        eventsChannel.send(AllEvents.Success(it.data))
                    }
                    else -> {
                        it as FileUploadResult.Error
                        eventsChannel.send(AllEvents.DynamicError(it.message))
                    }
                }
            }
        }
    }
}