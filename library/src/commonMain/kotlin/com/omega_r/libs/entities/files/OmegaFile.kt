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

    suspend fun isExist(holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current): Boolean? {
        return with(holder.getProcessor(this)) {
            exist()
        }
    }

    suspend fun getInput(holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current): Input? {
        return with(holder.getProcessor(this)) {
            input()
        }
    }

    suspend fun getOutput(holder: OmegaFileProcessorsHolder = OmegaFileProcessorsHolder.current): Output? {
        return with(holder.getProcessor(this)) {
            output()
        }
    }

}