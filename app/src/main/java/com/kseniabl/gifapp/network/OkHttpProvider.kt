package com.kseniabl.gifapp.network

import com.google.gson.Gson
import com.kseniabl.gifapp.network.models.OkkHttpData
import okhttp3.OkHttpClient

object OkHttpProvider {

    val okkHttpData = OkkHttpData(provideOkHttpClient(), provideBaseURL(), provideGson())

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    private fun provideGson() = Gson()

    private fun provideBaseURL() = "https://api.giphy.com/v1/gifs/search"
}