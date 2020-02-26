package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

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

}

fun OmegaImage.Companion.from(bitmap: Bitmap): OmegaImage = OmegaBitmapImage(bitmap)