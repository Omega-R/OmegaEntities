package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.styles.TextStyle

interface Text : TextHolder {

    companion object {

        fun from(value: String): Text = StringText(value)

        fun from(sequence: CharSequence): Text = StringText(sequence.toString())

        fun from(holder: StringHolder): Text = StringText(holder.string)

        fun from(vararg text: Text): Text = ArrayText(*text)

        fun from(list: List<Text>): Text = ArrayText(list)

        fun fromRes(resName: String) : Text = ResourceText(StringNamedResource(resName))

    }

    override val text: Text
        get() = this

    val isEmpty: Boolean

    operator fun plus(text: Text): Text = TextBuilder.BuilderText(this) + text

    operator fun plus(holder: TextHolder): Text = this + holder.text

    operator fun plus(string: String): Text = TextBuilder.BuilderText(this) + string

    operator fun plus(textStyle: TextStyle): Text = StyledText(this, textStyle)

}


interface StringHolder {

    val string: String?

}

interface TextHolder {

    val text: Text

}