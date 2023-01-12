package com.app.ktorcrud.apicall

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.FileUploadResult
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.response.UpdateUserResponse
import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.Either
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Created by Priyanka.
 */
interface ApiServiceClass {
    suspend fun login(loginRequestModel: LoginRequestModel): Either<String, LoginResponse>
    suspend fun getUserList(page: Int): Either<String, UsersListResponse>
    suspend fun updateUser(
        page: Int,
        updateUserRequest: UpdateUserRequest
    ): Either<String, UpdateUserResponse>

    suspend fun deleteUser(page: Int): Either<String, JsonObject>
    fun uploadImage(file: File): Flow<FileUploadResult>
    fun downloadImage(file: String): Flow<FileUploadResult>
}