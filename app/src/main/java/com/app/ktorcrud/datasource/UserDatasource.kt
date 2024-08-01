package com.app.ktorcrud.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.apicall.errorMessage
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.AllEvents

const val PAGE_SIZE = 6

class UserDatasource(
    private val apiService: ApiServiceImpl,
    val exceptionCallback: (AllEvents.DynamicError) -> Unit
) :
    PagingSource<Int, Any>() {

    private var userModel: UsersListResponse? = UsersListResponse()
    private var exception: String? = ""
    private val _userListResponse = MutableLiveData<ArrayList<Data>?>()
    private val userListResponse get() = _userListResponse
    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        try {
            var nextPage: Int? = params.key ?: 1
            if (nextPage!! <= userModel?.total_pages!!) {
                apiService.getUserList(nextPage).either({
                    exception = it
                    exceptionCallback(AllEvents.DynamicError(it))
                }) {
                    userModel = it
                    userListResponse.postValue(userModel?.data)
                }
            } else {
                userModel=null
                nextPage = null
            }
            return LoadResult.Page(
                data = userModel?.data ?: emptyList(),
                prevKey = if (nextPage == 1 || nextPage == null) null else nextPage - 1,
                nextKey = if (nextPage == null) null else nextPage + 1
            )
        } catch (e: Exception) {
            exceptionCallback(AllEvents.DynamicError(e.errorMessage() as String))
            return LoadResult.Error(e)
        }
    }
}