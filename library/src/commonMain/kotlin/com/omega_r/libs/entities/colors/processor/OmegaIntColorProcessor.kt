package com.omega_r.libs.entities.colors.processor

import com.omega_r.libs.entities.colors.OmegaIntColor

class OmegaIntColorProcessor : OmegaColorProcessor<OmegaIntColor> {

    override fun OmegaIntColor.extract(): Int? = colorInt

}