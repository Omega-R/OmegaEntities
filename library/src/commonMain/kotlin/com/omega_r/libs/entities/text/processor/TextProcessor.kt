package com.omega_r.libs.entities.text.processor

import com.omega_r.libs.entities.text.Text

abstract class TextProcessor<T: Text> {
    abstract fun T.extract(): String?
}