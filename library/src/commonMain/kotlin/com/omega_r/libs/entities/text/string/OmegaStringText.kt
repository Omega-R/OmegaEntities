package com.omega_r.libs.entities.text.string

import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextProcessorsHolder

data class OmegaStringText(val value: String?) : OmegaText {

    override fun getCharSequence(
        extractor: OmegaResourceExtractor,
        processorsHolder: OmegaTextProcessorsHolder
    ): CharSequence? = value

}