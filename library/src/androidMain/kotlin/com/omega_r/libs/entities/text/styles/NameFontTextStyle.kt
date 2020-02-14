package com.omega_r.libs.entities.text.styles

import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.toText

class NameFontTextStyle internal constructor(val fontName: OmegaText): OmegaTextStyle

fun OmegaTextStyle.Companion.from(fontName: OmegaText): OmegaTextStyle = NameFontTextStyle(fontName)

fun OmegaTextStyle.Companion.from(fontName: String): OmegaTextStyle = NameFontTextStyle(fontName.toText())