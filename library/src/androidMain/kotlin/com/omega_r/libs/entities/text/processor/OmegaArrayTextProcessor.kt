package com.omega_r.libs.entities.text.processor

import android.text.SpannableStringBuilder
import com.omega_r.libs.entities.text.OmegaArrayText

actual class OmegaArrayTextProcessor : OmegaTextProcessor<OmegaArrayText> {

    override fun OmegaArrayText.extract(): CharSequence? {
        val builder = SpannableStringBuilder()
        list.forEach { omegaText ->
            omegaText.getCharSequence()?.let { charSequence ->
                builder.append(charSequence)
            }
        }
        return builder
    }

}