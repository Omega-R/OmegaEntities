package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.extensions.asInput
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import java.io.ByteArrayInputStream
import java.io.InputStream

data class OmegaByteArrayImage(val byteArray: ByteArray) : BaseBitmapImage(), OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaByteArrayImage::class, Processor())
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OmegaByteArrayImage

        if (!byteArray.contentEquals(other.byteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        return byteArray.contentHashCode()
    }

    class Processor : BaseBitmapImage.Processor<OmegaByteArrayImage>(true) {

        override suspend fun OmegaByteArrayImage.input(
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): Input? = byteArray.asInput()

        override suspend fun OmegaByteArrayImage.getInputStream(
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = ByteArrayInputStream(byteArray)

        override suspend fun OmegaByteArrayImage.getBitmap(
                extractor: OmegaResourceExtractor,
                width: Int?,
                height: Int?
        ): Bitmap? = byteArray.toBitmap(width, height)

    }

}

fun OmegaImage.Companion.from(byteArray: ByteArray): OmegaImage = OmegaByteArrayImage(byteArray)