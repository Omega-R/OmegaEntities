package com.omega_r.libs.entities.text.plurals

import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.resource.OmegaResourceText

class OmegaPluralsText(
        resource: OmegaResource<*>,
        val quantity: Int,
        vararg formatArgs: Any = emptyArray()
) : OmegaResourceText(resource, *formatArgs)