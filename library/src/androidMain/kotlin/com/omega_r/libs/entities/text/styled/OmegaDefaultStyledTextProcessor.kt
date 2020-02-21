package com.omega_r.libs.entities.text.styled

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.*
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.styled.styles.*

actual object OmegaDefaultStyledTextProcessor : OmegaStyledTextProcessor {

    actual override fun OmegaStyledText.extract(resourceExtractor: OmegaResourceExtractor): CharSequence? =
        sourceText.getCharSequence()
            ?.applyStyle(
                styles = styles,
                extractor = resourceExtractor
            )


    private fun CharSequence.applyStyle(
        styles: Array<out OmegaTextStyle>,
        extractor: OmegaResourceExtractor
    ): CharSequence {
        return this
            .getSpannableString()
            .apply {
                styles.forEach {
                    applyStyle(it, extractor)
                }
            }

    }

    private fun CharSequence.getSpannableString(): SpannableString {
        if (this is SpannableString) return this
        return SpannableString(this)
    }

    private fun SpannableString.applyStyle(
        style: OmegaTextStyle,
        extractor: OmegaResourceExtractor
    ): SpannableString {
        when (style) {
            is OmegaArrayTextStyle -> {
                style.array.forEach { applyStyle(it, extractor) }
            }

            is OmegaColorTextStyle -> {
                val colorInt = style.color.getColorInt(extractor = extractor)
                setSpan(ForegroundColorSpan(colorInt))
            }

            is OmegaDecorationTextStyle -> {
                style.styles.forEach {
                    when (it) {
                        OmegaDecorationTextStyle.DecorationStyle.UNDERLINE -> setSpan(UnderlineSpan())
                        OmegaDecorationTextStyle.DecorationStyle.STRIKETHROUGH -> setSpan(StrikethroughSpan())
                    }
                }
            }
            is OmegaFontStyleTextStyle -> {
                if (style.styles.isEmpty()) {
                    setSpan(StyleSpan(Typeface.NORMAL))
                } else {
                    val isBold = OmegaFontStyleTextStyle.Style.BOLD in style.styles
                    val isItalic = OmegaFontStyleTextStyle.Style.ITALIC in style.styles
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