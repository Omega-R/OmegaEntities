package com.omega_r.libs.entities.texts

import com.omega_r.libs.entities.texts.styles.TextStyle
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

interface Text : TextHolder {

    companion object {

        @JvmStatic
        @JvmOverloads
        fun from(value: String): Text = StringText(value)

        @JvmStatic
        @JvmOverloads
        fun from(holder: StringHolder): Text = StringText(holder.getString())

        @JvmStatic
        @JvmOverloads
        fun from(vararg text: Text): Text = ArrayText(*text)

        @JvmStatic
        @JvmOverloads
        fun from(list: List<Text>): Text = ArrayText(list)

    }

    val isEmpty: Boolean

    operator fun plus(text: Text): Text {
        return TextBuilder.BuilderText(this) + text
    }

    operator fun plus(text: TextHolder): Text {
        return this + text.getText()
    }

    operator fun plus(string: String): Text {
        return TextBuilder.BuilderText(this) + string
    }

    operator fun plus(textStyle: TextStyle): Text {
        return StyledText(this, textStyle)
    }

    override fun getText(): Text = this

}


interface StringHolder {

    fun getString(): String?

}

interface TextHolder {

    fun getText(): Text

}