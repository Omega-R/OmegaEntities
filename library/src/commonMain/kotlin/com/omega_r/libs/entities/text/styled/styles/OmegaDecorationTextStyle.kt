package com.omega_r.libs.entities.text.styled.styles

class OmegaDecorationTextStyle(vararg val styles: DecorationStyle): OmegaTextStyle {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as OmegaDecorationTextStyle

        if (!styles.contentEquals(other.styles)) return false

        return true
    }

    override fun hashCode(): Int {
        return styles.contentHashCode()
    }

    override fun toString(): String {
        return "OmegaDecorationTextStyle(styles=${styles.contentToString()})"
    }

    enum class DecorationStyle {
        UNDERLINE, STRIKETHROUGH
    }

}