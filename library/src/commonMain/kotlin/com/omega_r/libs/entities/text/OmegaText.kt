package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.OmegaTextProcessors
import com.omega_r.libs.entities.text.styles.TextStyle

interface OmegaText : OmegaTextHolder {

    companion object {

        fun from(value: String): OmegaText = StringOmegaText(value)

        fun from(sequence: CharSequence): OmegaText = StringOmegaText(sequence.toString())

        fun from(holder: OmegaStringHolder): OmegaText = StringOmegaText(holder.string)

        fun from(vararg text: OmegaText): OmegaText = ArrayOmegaText(*text)

        fun from(list: List<OmegaText>): OmegaText = ArrayOmegaText(list)

    }

    override val text: OmegaText
        get() = this

    val isEmpty: Boolean

    operator fun plus(text: OmegaText): OmegaText = TextBuilder.BuilderOmegaText(this) + text

    operator fun plus(holder: OmegaTextHolder): OmegaText = this + holder.text

    operator fun plus(string: String): OmegaText = TextBuilder.BuilderOmegaText(this) + string

    operator fun plus(textStyle: TextStyle): OmegaText = StyledOmegaText(this, textStyle)

    fun getString() : String? = with(OmegaTextProcessors) { extract() }

}

interface OmegaStringHolder {

    val string: String?

}

interface OmegaTextHolder {

    val text: OmegaText

}