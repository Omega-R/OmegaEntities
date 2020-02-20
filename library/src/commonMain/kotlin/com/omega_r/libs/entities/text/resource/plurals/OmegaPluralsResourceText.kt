package com.omega_r.libs.entities.text.resource.plurals

import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.resource.OmegaResourceText

class OmegaPluralsResourceText(
    resource: OmegaResource.Plurals,
    val quantity: Int,
    vararg formatArgs: Any = emptyArray()
) : OmegaResourceText<OmegaResource.Plurals>(resource, formatArgs) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OmegaPluralsResourceText

        if (resource != other.resource) return false
        if (quantity != other.quantity) return false
        if (!formatArgs.contentEquals(other.formatArgs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = resource.hashCode()
        result = 31 * result + quantity
        result = 31 * result + formatArgs.contentHashCode()
        return result
    }
}