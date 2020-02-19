package com.omega_r.libs.entities.text.styled.styles

class OmegaFontStyleTextStyle(vararg val styles: Style): OmegaTextStyle {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OmegaFontStyleTextStyle

        if (!styles.contentEquals(other.styles)) return false

        return true
    }

    override fun hashCode(): Int {
        return styles.contentHashCode()
    }

    enum class Style {
        BOLD, ITALIC
    }


}