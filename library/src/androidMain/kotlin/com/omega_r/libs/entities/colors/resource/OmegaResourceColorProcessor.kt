package com.omega_r.libs.entities.colors.resource

import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.text.resource.OmegaResources

actual object OmegaResourceColorProcessor : OmegaColorProcessor<OmegaResourceColor> {

    override fun OmegaResourceColor.extract(): Int? {
        return OmegaResources.resources.getColor(resource.id)
    }

}