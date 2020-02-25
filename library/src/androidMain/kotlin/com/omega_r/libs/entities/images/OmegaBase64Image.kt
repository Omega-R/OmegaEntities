package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import android.util.Base64
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import java.io.ByteArrayInputStream
import java.io.InputStream

private const val COMMA = ","

actual class OmegaBase64Image(actual val base64String: String, val flags: Int) : OmegaImage, BaseBitmapImage() {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaBase64Image::class, Processor())
        }
    }

    class Processor : BaseBitmapImage.Processor<OmegaBase64Image>(true), OmegaProcessor<OmegaBase64Image> {

        override suspend fun OmegaBase64Image.getInputStream(
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = toByteArray()?.let { ByteArrayInputStream(it) }

        override suspend fun OmegaBase64Image.getBitmap(
                extractor: OmegaResourceExtractor,
                width: Int?,
                height: Int?
        ): Bitmap? = toByteArray()?.toBitmap(width, height)

        private fun OmegaBase64Image.toByteArray(): ByteArray? {
            val base64String = base64String
            val position = base64String.indexOf(COMMA)
            val data = if (position != -1) base64String.substring(position + 1) else base64String
            return Base64.decode(data, flags)
        }

    }

}

fun OmegaImage.Companion.from(base64String: String, flags: Int): OmegaImage = OmegaBase64Image(base64String, flags)