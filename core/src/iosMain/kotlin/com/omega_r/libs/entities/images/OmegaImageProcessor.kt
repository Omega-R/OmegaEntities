package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input

actual interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor<T> {

    actual suspend fun getInput(
        entity: T,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): Input?


}