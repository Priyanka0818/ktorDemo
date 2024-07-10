package com.app.ktorcrud.apicall

import android.util.Log
import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.*
import com.app.ktorcrud.utils.Either
import com.app.ktorcrud.utils.Failure
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.utils.io.charsets.*
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Created by Priyanka.
 */
class ApiServiceImpl(private val apiService: ApiService) : ApiServiceClass {

    override suspend fun getUserList(page: Int): Either<String, UsersListResponse> {
        return try {
            Either.Right(apiService.getUsers(page))
        } catch (ex: Exception) {
            Either.Left(ex.errorMessage() as String)
        }
    }
}


suspend fun Exception.errorMessage(): Any =
    when (this) {
        is ClientRequestException -> {
            response.readText(Charset.defaultCharset())
        }
        is ResponseException -> {
            if (response.status.value == 404) {
                response.status.description
            } else {
                Gson().fromJson(
                    response.readText(Charset.defaultCharset()),
                    CommonErrorResponse::class.java
                ).error!!
            }
        }
        else -> {
            localizedMessage!!
        }
    }


fun Exception.toCustomExceptions() = when (this) {
    is ServerResponseException -> Failure.HttpErrorInternalServerError(this)
    is ClientRequestException ->
        when (this.response.status.value) {
            400 -> Failure.HttpErrorBadRequest(this)
            401 -> Failure.HttpErrorUnauthorized(this)
            403 -> Failure.HttpErrorForbidden(this)
            404 -> Failure.HttpErrorNotFound(this)
            405 -> Failure.MethodNotAllowed(this)
            else -> Failure.HttpError(this)
        }
    is RedirectResponseException -> Failure.HttpError(this)
    else -> Failure.GenericError(this)
}
