package com.omega_r.libs.entities.text.styled.styles

import com.omega_r.libs.entities.OmegaEntity

interface OmegaTextStyle : OmegaEntity {

    operator fun plus(style: OmegaTextStyle): OmegaTextStyle = OmegaArrayTextStyle(this, style)


}