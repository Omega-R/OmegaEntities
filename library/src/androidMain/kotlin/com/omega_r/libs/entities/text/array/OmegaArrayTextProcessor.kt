package com.omega_r.libs.entities.text.array

import android.text.SpannableStringBuilder
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.OmegaTextProcessor

actual object OmegaArrayTextProcessor : OmegaTextProcessor<OmegaArrayText> {

    override fun extract(entity: OmegaArrayText, resourceExtractor: OmegaResourceExtractor): CharSequence? {
        val builder = SpannableStringBuilder()
        entity.array.forEach { omegaText ->
            omegaText.getCharSequence()?.let { charSequence ->
                builder.append(charSequence)
            }
        }
        return builder
    }

}