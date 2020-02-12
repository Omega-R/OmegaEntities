package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.OmegaResource

open class OmegaResourceText<T>(
        val resource: OmegaResource<T>,
        vararg val formatArgs: Any = emptyArray()
) : OmegaText