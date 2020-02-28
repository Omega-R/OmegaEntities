package com.omega_r.libs.entities.colors

import com.omega_r.libs.entities.colors.argb.OmegaArgbColor
import com.omega_r.libs.entities.colors.argb.OmegaArgbColorProcessor
import com.omega_r.libs.entities.colors.hex.OmegaHexColor
import com.omega_r.libs.entities.colors.hex.OmegaHexColorProcessor
import com.omega_r.libs.entities.colors.integer.OmegaIntColor
import com.omega_r.libs.entities.colors.integer.OmegaIntColorProcessor
import com.omega_r.libs.entities.colors.name.OmegaNameColor
import com.omega_r.libs.entities.colors.name.OmegaNameColorProcessor
import com.omega_r.libs.entities.colors.resource.OmegaResourceColor
import com.omega_r.libs.entities.colors.resource.OmegaResourceColorProcessor
import com.omega_r.libs.entities.processors.ProcessorsHolder


interface OmegaColorProcessorsHolder : ProcessorsHolder<OmegaColor, OmegaColorProcessor<OmegaColor>> {

    companion object {

        var current: OmegaColorProcessorsHolder = Default

    }

    object Default : ProcessorsHolder.Default<OmegaColor, OmegaColorProcessor<OmegaColor>>(), OmegaColorProcessorsHolder {

        init {
            addProcessor(OmegaResourceColor::class,  OmegaResourceColorProcessor)
            addProcessor(OmegaIntColor::class,  OmegaIntColorProcessor)
            addProcessor(OmegaNameColor::class,  OmegaNameColorProcessor)
            addProcessor(OmegaHexColor::class,  OmegaHexColorProcessor)
            addProcessor(OmegaArgbColor::class,  OmegaArgbColorProcessor)
        }

    }

}

