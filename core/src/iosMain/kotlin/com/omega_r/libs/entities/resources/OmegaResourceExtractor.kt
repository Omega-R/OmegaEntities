package com.omega_r.libs.entities.resources

import com.omega_r.libs.entities.extensions.toRgbInt
import platform.Foundation.NSArray
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.localizedStringWithFormat
import platform.UIKit.UIColor
import platform.UIKit.colorNamed

actual open class OmegaResourceExtractor(private val tableName: String = TABLE_NAME) {

    companion object {
        private const val TABLE_NAME = "Localizable"
    }

    actual fun getCharSequence(resource: OmegaResource.Text): CharSequence? =
        NSBundle.mainBundle.localizedStringForKey(resource.id, resource.id, tableName)

    actual fun getPluralsChatSequence(
        resource: OmegaResource.Plurals,
        quantity: Int
    ): CharSequence? = NSString.localizedStringWithFormat(
        NSBundle.mainBundle.localizedStringForKey(resource.id, resource.id, tableName),
        quantity
    )

    actual fun getCharSequenceArray(resource: OmegaResource.TextArray): Array<CharSequence> =
        (NSBundle.mainBundle.infoDictionary?.get(resource.id) as? NSArray as List<*>)
            .map { it as String }
            .toTypedArray()

    actual fun getColorInt(resource: OmegaResource.Color): Int = UIColor.colorNamed(resource.name)?.toRgbInt() ?: 0

    actual object Default : OmegaResourceExtractor()

}