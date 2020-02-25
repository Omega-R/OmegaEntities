package com.omega_r.libs.entities.resources

actual open class OmegaResourceExtractor {

    actual fun getCharSequence(resource: OmegaResource.Text): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getPluralsChatSequence(
        resource: OmegaResource.Plurals,
        quantity: Int
    ): CharSequence? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getCharSequenceArray(resource: OmegaResource.TextArray): Array<CharSequence> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getColorInt(resource: OmegaResource.Color): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual object Default : OmegaResourceExtractor()

}