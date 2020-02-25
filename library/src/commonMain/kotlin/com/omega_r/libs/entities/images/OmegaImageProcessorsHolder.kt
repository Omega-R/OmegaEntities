package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.processors.ProcessorsHolder

interface OmegaImageProcessorsHolder : ProcessorsHolder<OmegaImage, OmegaImageProcessor<OmegaImage>> {

    companion object {

        var current: OmegaImageProcessorsHolder = Default

    }

    object Default : ProcessorsHolder.Default<OmegaImage, OmegaImageProcessor<OmegaImage>>(), OmegaImageProcessorsHolder

}