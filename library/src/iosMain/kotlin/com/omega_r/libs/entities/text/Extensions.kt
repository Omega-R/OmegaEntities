package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.processor.OmegaTextProcessors
import platform.UIKit.UILabel

fun UILabel.setText(text: OmegaText) {
    this@setText.text = text.getString()
}