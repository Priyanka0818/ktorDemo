package com.app.ktorcrud.apicall

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.response.UsersListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Priyanka.
 */
interface ApiService {
    @POST(ApiRoutes.LOGIN)
    suspend fun loginUser(@Body loginRequestModel: LoginRequestModel): Response<LoginResponse>

    @GET(ApiRoutes.USERS)
    suspend fun getUserList(@Query("page") page: Int): Response<UsersListResponse>

}