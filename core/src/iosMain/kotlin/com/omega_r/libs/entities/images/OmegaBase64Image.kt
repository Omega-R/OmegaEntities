package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.images.OmegaImage
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.*

actual class OmegaBase64Image(actual val base64String: String) : OmegaImage {

    override suspend fun getInput(
        holder: OmegaImageProcessorsHolder,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input? = holder.getProcessor(this).getInput(this, extractor, format, quality)

}