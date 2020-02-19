package com.omega_r.libs.entities.text.string

import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.OmegaTextProcessorsHolder

data class OmegaStringText(val value: String?) : OmegaText {

    override fun getCharSequence(holder: OmegaTextProcessorsHolder): CharSequence? = value

}