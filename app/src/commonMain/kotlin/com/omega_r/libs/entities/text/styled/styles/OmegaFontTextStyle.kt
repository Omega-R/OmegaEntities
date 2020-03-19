package com.omega_r.libs.entities.text.styled.styles

import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.toText

data class OmegaFontTextStyle(val fontName: OmegaText) : OmegaTextStyle {

    constructor(fontName: String) : this(fontName.toText())

}

