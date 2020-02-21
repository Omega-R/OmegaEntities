package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.OmegaEntity
import io.ktor.utils.io.core.Input

interface OmegaImage : OmegaEntity {

    companion object {

        fun from(url: String): OmegaImage = OmegaUrlImage(url)

    }

    suspend fun getInput(holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current): Input? = with(holder) {
        with(getProcessor() as OmegaImageProcessor<OmegaImage>) {
            input()
        }
    }

}