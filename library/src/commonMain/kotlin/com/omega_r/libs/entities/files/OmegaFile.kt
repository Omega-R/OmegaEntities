package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.OmegaEntity
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output

interface OmegaFile : OmegaEntity {

    companion object {
        // for extensions
    }

    val type: Type

    val mimeType: String

    val name: String

    suspend fun isExist(holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current): Boolean? = with(holder) {
        with(getProcessor() as OmegaFileProcessor<OmegaFile>) { exist() }
    }

    suspend fun getInput(holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current): Input? = with(holder) {
        with(getProcessor() as OmegaFileProcessor<OmegaFile>) { input() }
    }

    suspend fun getOutput(holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current): Output? = with(holder) {
        with(getProcessor() as OmegaFileProcessor<OmegaFile>) { output() }
    }

}