package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output

interface OmegaFile : OmegaEntity {

    companion object {
        // for extensions
    }

    val type: Type

    val mimeType: String

    val name: String

    suspend fun isExist(
            holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    ): Boolean? = holder.getProcessor(this).isExist(this, extractor)

    suspend fun getInput(
            holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    ): Input? = holder.getProcessor(this).getInput(this, extractor)

    suspend fun getOutput(
            holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    ): Output? = holder.getProcessor(this).getOutput(this, extractor)

}