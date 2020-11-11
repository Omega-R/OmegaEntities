package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import android.util.Base64
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.*
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

        override suspend fun getBitmap(
                entity: OmegaBase64Image,
                extractor: OmegaResourceExtractor,
                width: Int?,
                height: Int?
        ): Bitmap? = entity.toByteArray()?.toBitmap(width, height)

        override suspend fun getInputStream(
                entity: OmegaBase64Image,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = entity.toByteArray()?.let { ByteArrayInputStream(it) }

        private fun OmegaBase64Image.toByteArray(): ByteArray? {
            val base64String = base64String
            val position = base64String.indexOf(COMMA)
            val data = if (position != -1) base64String.substring(position + 1) else base64String
            return Base64.decode(data, flags)
        }

    }

    override suspend fun getInput(
        holder: OmegaImageProcessorsHolder,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input? = holder.getProcessor(this).getInput(this, extractor, format, quality)

}

fun OmegaImage.Companion.from(base64String: String, flags: Int): OmegaImage = OmegaBase64Image(base64String, flags)