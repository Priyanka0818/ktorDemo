package com.app.ktorcrud.repository

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.response.*
import com.app.ktorcrud.utils.Either
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Created by Priyanka.
 */
interface ApiServiceRepository {
    suspend fun login(loginRequestModel: LoginRequestModel): Either<String, LoginResponse>
    suspend fun getUserList(page: Int): Either<String, UsersListResponse>
    suspend fun uploadImage(file: File): Either<FileUploadErrorResponse,FileUploadResponse>
}