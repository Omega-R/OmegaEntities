package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.OmegaResource

class OmegaPluralsText<T>(
        resource: OmegaResource<T>,
        val quantity: Int,
        vararg formatArgs: Any = emptyArray()
) : OmegaResourceText<T>(resource, *formatArgs)