package com.omega_r.libs.entities.text

import android.widget.TextView
import com.omega_r.libs.entities.OmegaResource

fun TextView.setText(text: OmegaText) {
    this@setText.text = text.getCharSequence()
}

fun OmegaText.Companion.from(id: Int): OmegaText = from(OmegaResource(id))