package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.processors.ProcessorsHolder
import kotlinx.io.core.Input
import kotlinx.io.core.Output

interface FileProcessorsHolder : ProcessorsHolder<OmegaFile, OmegaFileProcessor<OmegaFile>> {

    companion object {

        var current: FileProcessorsHolder = Default

    }

    object Default : ProcessorsHolder.Default<OmegaFile, OmegaFileProcessor<OmegaFile>>(), FileProcessorsHolder {
        init {

        }
    }

}