package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.processors.ProcessorsHolder

interface OmegaFileProcessorsHolder : ProcessorsHolder<OmegaFile, OmegaFileProcessor<out OmegaFile>> {

    companion object {

        var current: OmegaFileProcessorsHolder = Default

    }

    object Default : ProcessorsHolder.Default<OmegaFile, OmegaFileProcessor<out OmegaFile>>(), OmegaFileProcessorsHolder

}