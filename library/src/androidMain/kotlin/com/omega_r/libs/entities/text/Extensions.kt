package com.omega_r.libs.entities.text

import android.widget.TextView
import com.omega_r.libs.entities.text.processor.OmegaTextProcessors

fun TextView.setText(text: OmegaText) {
    this@setText.text = text.getString()
}