package com.app.ktorcrud.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.app.ktorcrud.R
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.datasource.PAGE_SIZE
import com.app.ktorcrud.datasource.UserDatasource
import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.utils.validateEmail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by Priyanka.
 */
class LoginViewModel(val apiServiceImpl: ApiServiceImpl) :
    ViewModel() {

    val isNetworkAvailable = MutableLiveData<Boolean?>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var name = ObservableField<String>()
    var job = ObservableField<String>()
    internal val eventsChannel = Channel<AllEvents>()
    val allEventsFlow = eventsChannel.receiveAsFlow()
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    private val loginResponse get() = _loginResponse

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
            UserDatasource(apiServiceImpl)
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
                            Log.e("Logger", "onCreate: $it")
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
}