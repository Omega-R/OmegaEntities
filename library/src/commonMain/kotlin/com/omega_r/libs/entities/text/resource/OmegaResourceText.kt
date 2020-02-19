package com.omega_r.libs.entities.text.resource

import com.omega_r.libs.entities.OmegaResource.Text
import com.omega_r.libs.entities.text.OmegaText

open class OmegaResourceText(
    val resource: Text,
    vararg val formatArgs: Any = emptyArray()
) : OmegaText