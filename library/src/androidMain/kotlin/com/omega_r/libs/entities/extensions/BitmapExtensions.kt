package com.omega_r.libs.entities.extensions

import android.graphics.Bitmap
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

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
