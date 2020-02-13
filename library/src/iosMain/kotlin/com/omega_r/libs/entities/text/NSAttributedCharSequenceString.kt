package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.OmegaTextProcessor
import platform.Foundation.NSAttributedString
import platform.Foundation.NSBundle

//class ResOmegaTextProcessor(
//        private val bundle: NSBundle = NSBundle.mainBundle,
//        private val tableName: String = "Localizable"
//) : OmegaTextProcessor<OmegaResourceText>() {
//
//    override fun OmegaResourceText.getString(): String? {
//        platform.Foundation.NSString
//        return (this.resource as? StringNamedOmegaResource)?.let {
//            bundle.localizedStringForKey(it.id, null, tableName)
//        }
//    }
//
//}

class NSAttributedCharSequenceString(coder: platform.Foundation.NSCoder) : NSAttributedString(coder), CharSequence {

    override val length: Int
        get() = string().length

    override fun get(index: Int): Char = string()[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = string().subSequence(startIndex, endIndex)

}