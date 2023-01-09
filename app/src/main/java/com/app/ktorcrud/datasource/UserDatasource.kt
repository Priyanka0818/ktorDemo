package com.app.ktorcrud.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.viewmodel.LoginViewModel

const val PAGE_SIZE = 6

class UserDatasource(private val apiService: ApiServiceImpl) :
    PagingSource<Int, Data>() {

    private var userModel: UsersListResponse? = UsersListResponse()
    private var exception: String? = ""
    private val _userListResponse = MutableLiveData<ArrayList<Data>?>()
    private val userListResponse get() = _userListResponse
    private val excep get() = MutableLiveData<String>()

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            var nextPage: Int? = params.key ?: 1
            if (nextPage!! <= userModel?.total_pages!!) {
                apiService.getUserList(nextPage).either({
                    exception = it
                    excep.postValue(exception)
                }) {
                    userModel = it
                    userListResponse.postValue(userModel?.data)
                }
            } else {
                nextPage = null
            }
            return LoadResult.Page(
                data = userModel?.data ?: emptyList(),
                prevKey = if (nextPage == 1) null else nextPage!! - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}