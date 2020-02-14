package com.omega_r.libs.entities.text.styles

import com.omega_r.libs.entities.OmegaEntity

interface OmegaTextStyle : OmegaEntity {

    companion object {

        fun from(vararg styles: OmegaTextStyle): OmegaTextStyle = OmegaArrayTextStyle(*styles)

    }

}