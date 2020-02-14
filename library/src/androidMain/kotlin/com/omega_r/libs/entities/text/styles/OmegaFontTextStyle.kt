package com.omega_r.libs.entities.text.styles

import android.graphics.Typeface
import com.omega_r.libs.entities.text.styles.OmegaFontTextStyle.Companion.boldItalicTextStyle
import com.omega_r.libs.entities.text.styles.OmegaFontTextStyle.Companion.boldTextStyle
import com.omega_r.libs.entities.text.styles.OmegaFontTextStyle.Companion.italicTextStyle
import com.omega_r.libs.entities.text.styles.OmegaFontTextStyle.Companion.normalTextStyle

class OmegaFontTextStyle private constructor(val typeFaceStyle: Int) : OmegaTextStyle {

    companion object {

        val normalTextStyle = OmegaFontTextStyle(Typeface.NORMAL)

        val boldTextStyle = OmegaFontTextStyle(Typeface.BOLD)

        val italicTextStyle = OmegaFontTextStyle(Typeface.ITALIC)

        val boldItalicTextStyle = OmegaFontTextStyle(Typeface.BOLD_ITALIC)

    }

}

fun OmegaTextStyle.Companion.normal(): OmegaTextStyle = normalTextStyle

fun OmegaTextStyle.Companion.bold(): OmegaTextStyle = boldTextStyle

fun OmegaTextStyle.Companion.italic(): OmegaTextStyle = italicTextStyle

fun OmegaTextStyle.Companion.boldItalic(): OmegaTextStyle = boldItalicTextStyle