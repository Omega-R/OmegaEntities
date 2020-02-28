package com.omega_r.libs.entities.text.resource

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.text.OmegaText

abstract class OmegaResourceText<R: OmegaResource>(val resource: R, vararg val formatArgs: Any): OmegaText {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OmegaResourceText<*>

        if (resource != other.resource) return false
        if (!formatArgs.contentEquals(other.formatArgs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = resource.hashCode()
        result = 31 * result + formatArgs.contentHashCode()
        return result
    }

}