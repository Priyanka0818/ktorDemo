package com.app.ktorcrud.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Environment
import android.util.Patterns
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import com.app.ktorcrud.response.CommonErrorResponse
import com.bumptech.glide.Glide
import com.google.gson.Gson
import io.ktor.client.features.*
import io.ktor.client.statement.*
import java.io.File
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

/**
 * Created by Priyanka.
 */
fun Context.isConnectedToInternet(): Boolean {
    var isOnline = false
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork
        if (nw == null) {
            isOnline = false
        } else {
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            isOnline =
                actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo
        isOnline = nwInfo != null && nwInfo.isConnected
    }
    return isOnline
}

fun String.validateEmail() = !Patterns.EMAIL_ADDRESS.matcher(this).matches()

@BindingAdapter("image")
fun ImageView.setImage(resource: Any) {
    Glide
        .with(this)
        .load(resource)
        .into(this)
}


suspend fun Exception.errorMessage() =
    when (this) {
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


fun Context.checkPermission(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED


fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}