package com.app.ktorcrud.repository

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.Either

/**
 * Created by Priyanka.
 */
interface ApiServiceRepository {
    suspend fun login(loginRequestModel: LoginRequestModel): Either<String, LoginResponse>
    suspend fun getUserList(page: Int): Either<String, UsersListResponse>
}