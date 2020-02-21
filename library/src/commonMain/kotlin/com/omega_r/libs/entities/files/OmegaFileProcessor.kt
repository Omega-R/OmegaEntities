package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.processors.OmegaProcessor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output

interface OmegaFileProcessor<T : OmegaFile> : OmegaProcessor {

    suspend fun T.exist(): Boolean? = null

    suspend fun T.input(): Input? = null

    suspend fun T.output(): Output? = null

}