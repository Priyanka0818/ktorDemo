package com.app.ktorcrud.apicall

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.response.FileUploadResponse
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.response.UsersListResponse
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Priyanka.
 */
interface ApiService {
    @POST(ApiRoutes.LOGIN)
    suspend fun loginUser(@Body loginRequestModel: LoginRequestModel): Response<LoginResponse>

    @GET(ApiRoutes.USERS)
    suspend fun getUserList(@Query("page") page: Int): Response<UsersListResponse>

    @Multipart
    @POST(ApiRoutes.UPLOAD_IMAGE)
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<FileUploadResponse>
}