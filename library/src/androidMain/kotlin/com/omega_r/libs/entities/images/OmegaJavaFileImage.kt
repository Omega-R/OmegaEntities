package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

data class OmegaJavaFileImage(val file: File) : BaseBitmapImage(), OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaJavaFileImage::class, Processor())
        }
    }

    class Processor : BaseBitmapImage.Processor<OmegaJavaFileImage>(true) {

        override suspend fun OmegaJavaFileImage.getBitmap(
                extractor: OmegaResourceExtractor,
                width: Int?,
                height: Int?
        ): Bitmap? = file.toBitmap(width, height)

        override suspend fun OmegaJavaFileImage.getInputStream(
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = FileInputStream(file)

    }

}

fun OmegaImage.Companion.from(file: File): OmegaImage = OmegaJavaFileImage(file)