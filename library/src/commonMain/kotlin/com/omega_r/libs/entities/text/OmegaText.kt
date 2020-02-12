package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.OmegaResource
import com.omega_r.libs.entities.text.processor.OmegaTextProcessors
import com.omega_r.libs.entities.text.styles.TextStyle
import kotlin.jvm.JvmStatic

interface OmegaText : OmegaTextHolder {

    companion object {

        @JvmStatic
        val empty: OmegaText = OmegaStringText(null)

        @JvmStatic
        fun from(value: String): OmegaText = OmegaStringText(value)

        @JvmStatic
        fun from(sequence: CharSequence): OmegaText = OmegaStringText(sequence.toString())

        @JvmStatic
        fun from(holder: OmegaStringHolder): OmegaText = OmegaStringText(holder.string)

        @JvmStatic
        fun from(resource: OmegaResource<*>): OmegaText = OmegaResourceText(resource)

        @JvmStatic
        fun from(resource: OmegaResource<*>, vararg formatArgs: Any): OmegaText = OmegaResourceText(resource, *formatArgs)

        @JvmStatic
        fun fromPlurals(resource: OmegaResource<*>, quantity: Int, vararg formatArgs: Any): OmegaText
                = OmegaPluralsText(resource, quantity, *formatArgs)

        @JvmStatic
        fun from(vararg text: OmegaText): OmegaText = OmegaArrayText(*text)

        @JvmStatic
        fun from(list: List<OmegaText>): OmegaText = OmegaArrayText(list)

        @JvmStatic
        fun from(text: OmegaText, style: TextStyle): OmegaText = StyledOmegaText(text, style)

    }

    override val text: OmegaText
        get() = this

    val isEmpty: Boolean
        get() = getString().isNullOrEmpty()

    operator fun plus(text: OmegaText): OmegaText = TextBuilder.BuilderOmegaText(this) + text

    operator fun plus(holder: OmegaTextHolder): OmegaText = this + holder.text

    operator fun plus(string: String): OmegaText = TextBuilder.BuilderOmegaText(this) + string

    operator fun plus(textStyle: TextStyle): OmegaText = StyledOmegaText(this, textStyle)

    fun getString(): String? = with(OmegaTextProcessors) { extract() }

}

interface OmegaStringHolder {

    val string: String?

}

interface OmegaTextHolder {

    val text: OmegaText

}