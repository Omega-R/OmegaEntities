package com.omega_r.libs.entities.text.styled.styles

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.colors.OmegaColor
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.styled.styles.OmegaDecorationTextStyle.DecorationStyle

interface OmegaTextStyle : OmegaEntity {

    companion object {

        fun from(vararg styles: OmegaTextStyle): OmegaTextStyle = OmegaArrayTextStyle(*styles)

        fun from(color: OmegaColor): OmegaTextStyle = OmegaColorTextStyle(color)

        fun from(vararg styles: DecorationStyle): OmegaTextStyle = OmegaDecorationTextStyle(*styles)

        fun from(vararg styles: OmegaFontStyleTextStyle.Style): OmegaTextStyle = OmegaFontStyleTextStyle(*styles)

        fun from(fontName: OmegaText): OmegaTextStyle = OmegaFontTextStyle(fontName)

    }

    operator fun plus(style: OmegaTextStyle): OmegaTextStyle = OmegaArrayTextStyle(this, style)

}