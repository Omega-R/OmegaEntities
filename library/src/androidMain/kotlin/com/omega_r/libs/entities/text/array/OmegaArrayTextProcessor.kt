package com.omega_r.libs.entities.text.array

import android.text.SpannableStringBuilder
import com.omega_r.libs.entities.text.OmegaTextProcessor

actual object OmegaArrayTextProcessor : OmegaTextProcessor<OmegaArrayText> {

    override fun OmegaArrayText.extract(): CharSequence? {
        val builder = SpannableStringBuilder()
        array.forEach { omegaText ->
            omegaText.getCharSequence()?.let { charSequence ->
                builder.append(charSequence)
            }
        }
        return builder
    }

}