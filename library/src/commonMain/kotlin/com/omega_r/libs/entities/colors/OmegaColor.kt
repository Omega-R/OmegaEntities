package com.omega_r.libs.entities.colors

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.colors.OmegaColorProcessorsHolder.Companion.current
import com.omega_r.libs.entities.colors.resource.OmegaResourceColor
import kotlin.jvm.JvmOverloads

interface OmegaColor : OmegaEntity {

    companion object {

        fun from(resource: OmegaResource.Colour): OmegaColor =
            OmegaResourceColor(resource)

    }

    @JvmOverloads
    fun getColorInt(processorsHolder: OmegaColorProcessorsHolder = current): Int? {
        return with(processorsHolder) { extract() }
    }

}