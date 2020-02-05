package com.omega_r.libs.entities.text

import android.widget.TextView
import com.omega_r.libs.entities.text.processor.TextProcessors

fun TextView.setText(text: Text, processors: TextProcessors = TextProcessors.current) {
    with(processors) {
        this@setText.text = text.extract()
    }
}