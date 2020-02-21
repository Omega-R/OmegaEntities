package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.processors.OmegaProcessor
import io.ktor.utils.io.core.Input

expect interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor {

    suspend fun T.input(): Input?

}