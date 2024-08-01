package com.app.ktorcrud.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.datasource.UserDatasource
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
        users.value = AllEvents.Loading(true)
        users.value = AllEvents.Success(pagedUserList)
    }

    private val pagedUserList =
        Pager(PagingConfig(pageSize = 5), pagingSourceFactory = {
            UserDatasource(apiServiceImpl) {
                viewModelScope.launch {
                    users.value = AllEvents.DynamicError(it.error)
                }
            }
        }).flow
}