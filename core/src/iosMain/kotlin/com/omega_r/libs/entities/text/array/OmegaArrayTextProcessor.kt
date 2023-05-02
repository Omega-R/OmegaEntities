package com.omega_r.libs.entities.text.array

import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.NSSpannableString
import com.omega_r.libs.entities.text.OmegaTextProcessor

actual object OmegaArrayTextProcessor : OmegaTextProcessor<OmegaArrayText> {

    override fun extract(entity: OmegaArrayText, extractor: OmegaResourceExtractor): CharSequence? {
        var spannableString = NSSpannableString.create()
        entity.array.forEach { omegaText ->
            omegaText.getCharSequence(extractor)?.let { charSequence ->
                spannableString += charSequence
            }
        }
        return spannableString
    }

}