package com.app.ktorcrud.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        var requestLog = String.format(
            "Sending request %s on %s%n%s",
            request.url, chain.connection(), request.headers
        )
        if (request.method.compareTo("post", ignoreCase = true) == 0) {
            requestLog = """
                
                $requestLog
                ${bodyToString(request)}
                """.trimIndent()
        }
        Log.e("LoggingInterceptor", "request\n$requestLog")
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        val responseLog = String.format(
            "Received response for %s in %.1fms%n%s",
            response.request.url, (t2 - t1) / 1e6, response.headers
        )
        val bodyString = response.body!!.string()
        Log.e("LoggingInterceptor", "response\n$bodyString")

//        Log.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);
        return response.newBuilder()
            .body(ResponseBody.create(response.body!!.contentType(), bodyString))
            .build()
    }

    companion object {
        fun bodyToString(request: Request): String {
            return try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body!!.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}