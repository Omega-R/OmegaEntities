package com.omega_r.libs.entities.resources

actual open class OmegaResourceExtractor {

    actual fun getCharSequence(resource: OmegaResource.Text): CharSequence? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    actual fun getPluralsChatSequence(
        resource: OmegaResource.Plurals,
        quantity: Int
    ): CharSequence? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    actual fun getCharSequenceArray(resource: OmegaResource.TextArray): Array<CharSequence> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return emptyArray()
    }

    actual fun getColorInt(resource: OmegaResource.Color): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return 1
    }

    actual object Default : OmegaResourceExtractor()

}