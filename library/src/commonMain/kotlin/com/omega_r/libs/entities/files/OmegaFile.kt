package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.OmegaEntity
import kotlinx.io.core.Input
import kotlinx.io.core.Output

interface OmegaFile : OmegaEntity {

    companion object {
        // for extensions
    }

    val type: Type

    val mimeType: String

    val name: String

    suspend fun isExist(holder: FileProcessorsHolder = FileProcessorsHolder.current): Boolean? = with(holder) {
        with(getProcessor()) { exist() }
    }

    suspend fun getInput(holder: FileProcessorsHolder = FileProcessorsHolder.current): Input? = with(holder) {
        with(getProcessor()) { input() }
    }

    suspend fun getOutput(holder: FileProcessorsHolder = FileProcessorsHolder.current): Output? = with(holder) {
        with(getProcessor()) { output() }
    }

}