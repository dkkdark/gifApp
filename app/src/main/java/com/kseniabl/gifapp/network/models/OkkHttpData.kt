package com.kseniabl.gifapp.network.models

import com.google.gson.Gson
import okhttp3.OkHttpClient

data class OkkHttpData(
    val client: OkHttpClient,
    val url: String,
    val gson: Gson
)