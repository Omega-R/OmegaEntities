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

        override suspend fun OmegaBitmapImage.getBitmap(extractor: OmegaResourceExtractor, width: Int?, height: Int?): Bitmap? =
                bitmap

    }

}

fun OmegaImage.Companion.from(bitmap: Bitmap): OmegaImage = OmegaBitmapImage(bitmap)