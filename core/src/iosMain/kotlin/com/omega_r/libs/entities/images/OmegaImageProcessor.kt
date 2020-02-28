package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.processors.OmegaProcessor
import io.ktor.utils.io.core.Input

actual interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor {

    actual suspend fun T.getInput(): Input?

}