package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.images.OmegaImage.Format
import com.omega_r.libs.entities.images.OmegaImage.Format.JPEG
import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input

expect interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor<T> {

    suspend fun T.input(extractor: OmegaResourceExtractor, format: Format = JPEG, quality: Int = 100): Input?

}