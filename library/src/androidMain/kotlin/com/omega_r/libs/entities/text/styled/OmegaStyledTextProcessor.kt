package com.omega_r.libs.entities.text.styled

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import com.omega_r.libs.entities.text.OmegaTextProcessor
import com.omega_r.libs.entities.text.styled.styles.OmegaDecorationTextStyle.DecorationStyle.*
import com.omega_r.libs.entities.text.styled.styles.OmegaFontStyleTextStyle.Style.*
import com.omega_r.libs.entities.text.styled.styles.OmegaDecorationTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaFontStyleTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaFontTextStyle
import com.omega_r.libs.entities.text.styled.styles.OmegaTextStyle

object OmegaStyledTextProcessor : OmegaTextProcessor<OmegaStyledText> {

    override fun OmegaStyledText.extract(): CharSequence? =
        sourceText.getCharSequence()?.let {
            applyStyle(
                it,
                styles = styles
            )
        }

    private fun applyStyle(charSequence: CharSequence, styles: Array<out OmegaTextStyle>): CharSequence {
        return charSequence
            .getSpannableString()
            .apply {
                styles.forEach {
                    applyStyle(it)
                }
            }

    }

    private fun CharSequence.getSpannableString(): SpannableString {
        if (this is SpannableString) return this
        return SpannableString(this)
    }

    private fun SpannableString.applyStyle(style: OmegaTextStyle): SpannableString {
        when (style) {
            is OmegaDecorationTextStyle -> {
                style.styles.forEach {
                    when (it) {
                        UNDERLINE -> setSpan(UnderlineSpan())
                        STRIKETHROUGH -> setSpan(StrikethroughSpan())
                    }
                }
            }
            is OmegaFontStyleTextStyle -> {
                if (style.styles.isEmpty()) {
                    setSpan(StyleSpan(Typeface.NORMAL))
                } else {
                    val isBold = BOLD in style.styles
                    val isItalic = ITALIC in style.styles
                    when {
                        isBold && isItalic -> setSpan(StyleSpan(Typeface.BOLD_ITALIC))
                        isBold -> setSpan(StyleSpan(Typeface.BOLD))
                        isItalic -> setSpan(StyleSpan(Typeface.ITALIC))
                    }
                }
            }
            is OmegaFontTextStyle -> setSpan(TypefaceSpan(style.fontName.getString()))

        }

        return this
    }

    private fun SpannableString.setSpan(span: Any) = setSpan(span, 0, length, 0)

}