package com.app.ktorcrud.di

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Created by Priyanka.
 */
private const val TIME_OUT = 60_000

val ktorHttpClient = HttpClient(Android) {

    defaultRequest {
//        host = "reqres.in/api"
        host = "api.upload.io/v2/accounts/FW25b1r"
//        host = "upcdn.io/FW25b1r"
        url {
            protocol = URLProtocol.HTTPS
        }
    }

    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger Ktor =>", message)
            }

        }
        level = LogLevel.ALL
    }

    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.Authorization, "Bearer public_FW25b1r73Z92jneE6jRB9JVpkRRn")
    }
}
