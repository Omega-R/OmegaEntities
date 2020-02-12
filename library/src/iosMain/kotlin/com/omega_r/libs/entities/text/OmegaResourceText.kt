package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.OmegaTextProcessor
import platform.Foundation.NSBundle

//class ResOmegaTextProcessor(
//    private val bundle: NSBundle = NSBundle.mainBundle,
//    private val tableName: String = "Localizable"
//) : OmegaTextProcessor<OmegaResourceText>() {
//
//    override fun OmegaResourceText.getString(): String? {
//        return (this.resource as? StringNamedOmegaResource)?.let {
//            bundle.localizedStringForKey(it.id, null, tableName)
//        }
//    }
//
//}