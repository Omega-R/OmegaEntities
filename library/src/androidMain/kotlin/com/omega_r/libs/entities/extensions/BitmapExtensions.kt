package com.omega_r.libs.entities.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.omega_r.libs.entities.images.OmegaImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun Bitmap?.toInputStream(format: OmegaImage.Format = OmegaImage.Format.JPEG, quality: Int = 100): InputStream =
        toInputStream(format.toCompressFormat(), quality)

fun Bitmap?.toInputStream(compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100): InputStream {
    val stream = ByteArrayOutputStream()
    val byteArray = if (this != null) {
        compress(compressFormat, quality, stream)
        stream.toByteArray()
    } else {
        ByteArray(0)
    }
    return ByteArrayInputStream(byteArray)
}

inline fun <R> Drawable.toBitmapAndRecycle(converter: Bitmap.() -> R): R {
    if (this is BitmapDrawable) {
        return converter(bitmap)
    }

    val newBitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)!!
    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)!!
    }

    try {
        val oldBounds = copyBounds()
        setBounds(0, 0, newBitmap.width, newBitmap.height)

        draw(Canvas(newBitmap))

        bounds = oldBounds
        return converter(newBitmap)
    } finally {
        newBitmap.recycle()
    }
}