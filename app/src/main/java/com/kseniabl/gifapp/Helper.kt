package com.kseniabl.gifapp

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object Helper {
    fun getShimmerDrawable(): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
    }
}