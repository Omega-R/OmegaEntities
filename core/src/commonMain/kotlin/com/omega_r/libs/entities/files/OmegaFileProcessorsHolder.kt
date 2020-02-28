package com.omega_r.libs.entities.files

import com.omega_r.libs.entities.files.url.OmegaUrlFile
import com.omega_r.libs.entities.files.url.OmegaUrlFileProcessor
import com.omega_r.libs.entities.processors.ProcessorsHolder

interface OmegaFileProcessorsHolder : ProcessorsHolder<OmegaFile, OmegaFileProcessor<OmegaFile>> {

    companion object {

        var current: OmegaFileProcessorsHolder = Default

    }

    object Default : ProcessorsHolder.Default<OmegaFile, OmegaFileProcessor<OmegaFile>>(), OmegaFileProcessorsHolder {

        init {
            addProcessor(OmegaUrlFile::class, OmegaUrlFileProcessor())
        }

    }

}