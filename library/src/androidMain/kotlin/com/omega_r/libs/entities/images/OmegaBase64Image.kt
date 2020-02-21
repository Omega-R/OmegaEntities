package com.omega_r.libs.entities.images

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import com.omega_r.libs.entities.decoders.toBitmap
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import java.io.ByteArrayInputStream

actual class OmegaBase64Image(actual val base64String: String, val flags: Int) : OmegaImage, BaseBitmapImage() {

    class Processor : BaseBitmapImage.Processor<OmegaBase64Image>(true) {

        override suspend fun OmegaBase64Image.getBitmap(context: Context, width: Int?, height: Int?): Bitmap? =
                toByteArray()?.toBitmap(width, height)

        override suspend fun OmegaBase64Image.input(): Input? = toByteArray()?.let { ByteArrayInputStream(it).asInput() }

        private fun OmegaBase64Image.toByteArray(): ByteArray? {
            val base64String = base64String
            val position = base64String.indexOf(",")
            val data = if (position != -1) base64String.substring(position + 1) else base64String
            return Base64.decode(data, flags)
        }

    }

}

fun OmegaImage.Companion.from(base64String: String, flags: Int): OmegaImage = OmegaBase64Image(base64String, flags)