package com.app.ktorcrud.apicall

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.response.LoginResponse
import retrofit2.Response
import retrofit2.http.POST

/**
 * Created by Priyanka.
 */
interface ApiService {
    @POST(ApiRoutes.LOGIN)
    suspend fun loginUser(loginRequestModel: LoginRequestModel): Response<LoginResponse>
}