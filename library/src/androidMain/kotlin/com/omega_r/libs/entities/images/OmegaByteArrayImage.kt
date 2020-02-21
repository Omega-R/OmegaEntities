package com.omega_r.libs.entities.images

import android.content.Context
import android.graphics.Bitmap
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.extensions.asInput
import io.ktor.utils.io.core.Input

data class OmegaByteArrayImage(val byteArray: ByteArray) : BaseBitmapImage(), OmegaImage {

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

        override suspend fun OmegaByteArrayImage.getBitmap(context: Context, width: Int?, height: Int?): Bitmap? =
                byteArray.toBitmap(width, height)

        override suspend fun OmegaByteArrayImage.input(): Input? = byteArray.asInput()

    }

}

fun OmegaImage.Companion.from(byteArray: ByteArray): OmegaImage = OmegaByteArrayImage(byteArray)