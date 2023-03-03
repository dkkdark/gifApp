package com.kseniabl.gifapp.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GifModel (
    val id: String,
    val url: String,
    val username: String,
    val title: String,
    val rating: String,
    val images: ImageModel
): Parcelable