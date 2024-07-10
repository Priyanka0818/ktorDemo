package com.app.ktorcrud.apicall

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.Either
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Created by Priyanka.
 */
interface ApiServiceClass {
    suspend fun getUserList(page: Int): Either<String, UsersListResponse>
}