package com.omega_r.libs.entities.colors

import com.omega_r.libs.entities.OmegaResource

interface OmegaColor {

    val colorInt: Int

    companion object {

        fun from(resource: OmegaResource<*>): OmegaColor = OmegaResourceColor(resource)

        fun from(colorInt: Int): OmegaColor = OmegaIntColor(colorInt)

    }

}