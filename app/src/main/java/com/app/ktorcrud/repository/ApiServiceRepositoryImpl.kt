package com.app.ktorcrud.repository

import com.app.ktorcrud.apicall.ApiService
import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.utils.Either

/**
 * Created by Priyanka.
 */
class ApiServiceRepositoryImpl(val apiService: ApiService) : ApiServiceRepository {
    override suspend fun login(loginRequestModel: LoginRequestModel): Either<String, LoginResponse> {
        val response = apiService.loginUser(loginRequestModel)
        return if (response.isSuccessful) {
            Either.Right(response.body()!!)
        } else {
            Either.Left("")
        }
    }
}