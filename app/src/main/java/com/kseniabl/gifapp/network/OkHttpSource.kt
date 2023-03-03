package com.kseniabl.gifapp.network

import android.util.Log
import com.google.gson.reflect.TypeToken
import com.kseniabl.gifapp.Constants.API_KEY
import com.kseniabl.gifapp.network.models.GifResponseBody
import com.kseniabl.gifapp.network.models.OkkHttpData
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class OkHttpSource(
    private val okkHttpData: OkkHttpData
): OkHttpSourceInterface {

    override suspend fun gifCall(query: String, limit: Int, offset: Int): GifResponseBody {
        val args = "?api_key=$API_KEY&q=$query&limit=$limit&offset=$offset"
        val request = Request.Builder()
            .get()
            .url("${okkHttpData.url}$args")
            .build()
        val call = okkHttpData.client.newCall(request)
        return awaitResponse(call)
    }

    private suspend fun awaitResponse(call: Call): GifResponseBody = suspendCancellableCoroutine { continuation ->
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful)
                        continuation.resumeWithException(CallException(response.code.toString()))
                    else
                        continuation.resume(parseToJson(response, GifResponseBody::class.java))
                }
            }
        })
        continuation.invokeOnCancellation {
            call.cancel()
        }
    }

    private fun parseToJson(response: Response, type: Class<GifResponseBody>): GifResponseBody {
        try {
            return okkHttpData.gson.fromJson(response.body!!.string(), type)
        } catch (e: Exception) {
            throw ParseJsonException(e)
        }
    }

    class ParseJsonException(e: Exception): Exception(e)
    class CallException(message: String): Exception(message)
}