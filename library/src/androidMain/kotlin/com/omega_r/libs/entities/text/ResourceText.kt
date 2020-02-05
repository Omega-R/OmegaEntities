package com.omega_r.libs.entities.text

import android.app.Application
import com.omega_r.libs.entities.text.processor.TextProcessor

actual class Resource actual constructor(private val _id: Int) {

    actual val id: Int
        get() = _id

    actual fun isEmpty(): Boolean = id <= 0

}

actual class ResourceText actual constructor(private val resource: Resource): Text {
    actual override val isEmpty: Boolean
        get() = resource.isEmpty()

    class Processor(private val application: Application) : TextProcessor<ResourceText>() {
        override fun ResourceText.extract(): String? {
            return null
//            application.getString(resource.id)
        }
    }
}