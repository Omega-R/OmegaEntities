package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.styles.TextStyle

data class StyledOmegaText(val sourceText: OmegaText, val style: TextStyle) : OmegaText {

    override val isEmpty: Boolean = sourceText.isEmpty

}