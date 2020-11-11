package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import com.omega_r.libs.entities.decoders.toBitmap
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.*
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

        override suspend fun getBitmap(
                entity: OmegaJavaFileImage,
                extractor: OmegaResourceExtractor,
                width: Int?,
                height: Int?
        ): Bitmap? = entity.file.toBitmap(width, height)

        override suspend fun getInputStream(
                entity: OmegaJavaFileImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): InputStream? = FileInputStream(entity.file)

    }

    override suspend fun getInput(
        holder: OmegaImageProcessorsHolder,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input? = holder.getProcessor(this).getInput(this, extractor, format, quality)

}

fun OmegaImage.Companion.from(file: File): OmegaImage = OmegaJavaFileImage(file)