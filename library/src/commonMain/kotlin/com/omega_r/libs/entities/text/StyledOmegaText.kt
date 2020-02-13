package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.styles.OmegaTextStyle

data class StyledOmegaText(val sourceText: OmegaText, val style: OmegaTextStyle) : OmegaText {

    override val isEmpty: Boolean
        get() = sourceText.isEmpty

}