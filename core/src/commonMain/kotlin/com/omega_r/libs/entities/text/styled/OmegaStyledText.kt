package com.omega_r.libs.entities.text.styled

import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.styled.styles.OmegaArrayTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaTextStyle

class OmegaStyledText(val sourceText: OmegaText, vararg val styles: OmegaTextStyle) : OmegaText {

    override fun plus(textStyle: OmegaTextStyle): OmegaText {
        val list = when (textStyle) {
            is OmegaArrayTextStyle -> {
                arrayOf(*styles, *textStyle.array)
            }
            else -> arrayOf(*styles, textStyle)
        }

        return OmegaStyledText(sourceText, *list)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OmegaStyledText

        if (sourceText != other.sourceText) return false
        if (!styles.contentEquals(other.styles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sourceText.hashCode()
        result = 31 * result + styles.contentHashCode()
        return result
    }


}