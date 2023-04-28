package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.processors.OmegaProcessor

actual class OmegaBase64Image(actual val base64String: String) : OmegaImage, BaseBitmapImage() {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaBase64Image::class, Processor())
        }
    }

    class Processor() : BaseBitmapImage.Processor<OmegaBase64Image>(), OmegaProcessor<OmegaBase64Image> {

    }

}