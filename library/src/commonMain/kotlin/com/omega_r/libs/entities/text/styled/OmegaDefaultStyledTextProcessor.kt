package com.omega_r.libs.entities.text.styled

expect object OmegaDefaultStyledTextProcessor : OmegaStyledTextProcessor {

    override fun OmegaStyledText.extract(): CharSequence?

}