package com.app.ktorcrud.repository

import android.util.Log
import com.app.ktorcrud.apicall.ApiService
import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.response.*
import com.app.ktorcrud.utils.Either
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import retrofit2.HttpException
import java.io.File

/**
 * Created by Priyanka.
 */
class ApiServiceRepositoryImpl(val apiService: ApiService) : ApiServiceRepository {
    override suspend fun login(loginRequestModel: LoginRequestModel): Either<String, LoginResponse> {
        val response = apiService.loginUser(loginRequestModel)
        return if (response.isSuccessful) {
            Either.Right(response.body()!!)
        } else {
            Either.Left(response.message())
        }
    }

    override suspend fun getUserList(page: Int): Either<String, UsersListResponse> {
        val response = apiService.getUserList(page)
        return if (response.isSuccessful) {
            Either.Right(response.body()!!)
        } else {
            Either.Left(response.message())
        }
    }

    override suspend fun uploadImage(file: File): Either<FileUploadErrorResponse, FileUploadResponse> {
        val filePart = MultipartBody.Part.createFormData(
            "file",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )

        val response = apiService.uploadImage(filePart)
        return if (response.isSuccessful) {
            Either.Right(response.body()!!)
        } else {
            val errorModel = Gson().fromJson<FileUploadErrorResponse>(
                HttpException(response).response()?.errorBody()?.string(),
                object : TypeToken<FileUploadErrorResponse>() {}.type
            )
            Either.Left(errorModel)
        }
    }

}