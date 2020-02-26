package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output

interface OmegaFileProcessor<T : OmegaFile> : OmegaProcessor<T> {

    suspend fun isExist(entity: T, extractor: OmegaResourceExtractor): Boolean? = null

    suspend fun getInput(entity: T, extractor: OmegaResourceExtractor): Input? = null

    suspend fun getOutput(entity: T, extractor: OmegaResourceExtractor): Output? = null

}