package com.omega_r.libs.entities.extensions

import android.graphics.Bitmap
import com.omega_r.libs.entities.images.OmegaImage
import com.omega_r.libs.entities.images.OmegaImage.Format.*

val OmegaImage.Companion.NO_PLACEHOLDER_RES: Int
    get() = 0

fun OmegaImage.Format.toCompressFormat(): Bitmap.CompressFormat {
    return when(this) {
        JPEG -> Bitmap.CompressFormat.JPEG
        PNG -> Bitmap.CompressFormat.PNG
        WEBP -> Bitmap.CompressFormat.WEBP
    }
}