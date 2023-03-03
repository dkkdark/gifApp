package com.kseniabl.gifapp.network

import com.kseniabl.gifapp.network.models.GifResponseBody

interface OkHttpSourceInterface {
    suspend fun gifCall(query: String, limit: Int, offset: Int): GifResponseBody
}