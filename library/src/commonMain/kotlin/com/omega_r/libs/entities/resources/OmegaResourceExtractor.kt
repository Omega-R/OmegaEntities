package com.omega_r.libs.entities.resources

expect open class OmegaResourceExtractor {

    fun getCharSequence(resource: OmegaResource.Text): CharSequence?

    fun getPluralsChatSequence(resource: OmegaResource.Plurals, quantity: Int): CharSequence?

    fun getCharSequenceArray(resource: OmegaResource.TextArray): Array<CharSequence>

    fun getColorInt(resource: OmegaResource.Colour): Int

    object Default: OmegaResourceExtractor

}