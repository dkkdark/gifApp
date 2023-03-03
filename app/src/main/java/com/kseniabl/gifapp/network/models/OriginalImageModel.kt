package com.kseniabl.gifapp.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OriginalImageModel (
    val url: String
): Parcelable