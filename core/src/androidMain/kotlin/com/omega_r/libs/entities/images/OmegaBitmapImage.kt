package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.*

data class OmegaBitmapImage(val bitmap: Bitmap) : BaseBitmapImage() {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaBitmapImage::class, Processor())
        }
    }

    class Processor : BaseBitmapImage.Processor<OmegaBitmapImage>(false) {

        override suspend fun getBitmap(
                entity: OmegaBitmapImage,
                extractor: OmegaResourceExtractor,
                width: Int?,
                height: Int?
        ): Bitmap? = entity.bitmap

    }

    override suspend fun getInput(
        holder: OmegaImageProcessorsHolder,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input? = holder.getProcessor(this).getInput(this, extractor, format, quality)

}

fun OmegaImage.Companion.from(bitmap: Bitmap): OmegaImage = OmegaBitmapImage(bitmap)