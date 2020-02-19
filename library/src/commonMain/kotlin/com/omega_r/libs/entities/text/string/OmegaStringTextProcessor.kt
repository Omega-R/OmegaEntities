package com.omega_r.libs.entities.text.string

import com.omega_r.libs.entities.text.OmegaTextProcessor

class OmegaStringTextProcessor : OmegaTextProcessor<OmegaStringText> {

    override fun OmegaStringText.extract(): CharSequence? = value

}