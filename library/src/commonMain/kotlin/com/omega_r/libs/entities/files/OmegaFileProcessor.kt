package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.processors.OmegaProcessor
import kotlinx.io.core.Input
import kotlinx.io.core.Output

interface OmegaFileProcessor<T : OmegaFile> : OmegaProcessor {

    suspend fun T.exist(): Boolean?

    suspend fun T.input(): Input?

    suspend fun T.output(): Output?

}