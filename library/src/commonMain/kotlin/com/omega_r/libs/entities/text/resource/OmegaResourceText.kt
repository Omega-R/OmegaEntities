package com.omega_r.libs.entities.text.resource

import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.OmegaText

open class OmegaResourceText(
    val resource: OmegaResource<*>,
    vararg val formatArgs: Any = emptyArray()
) : OmegaText