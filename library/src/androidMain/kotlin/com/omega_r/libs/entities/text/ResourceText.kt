package com.omega_r.libs.entities.text

import android.content.res.Resources
import com.omega_r.libs.entities.text.processor.TextProcessor

fun Text.Companion.fromRes(resId: Int) : Text = ResourceText(IntResource(resId))

class IntResTextProcessor(private val resources: Resources): TextProcessor<ResourceText>() {

    override fun ResourceText.extract(): String? {
        // TODO: implement StringNamedResource
        return (this.resource as? IntResource)?.let {
            try {
                resources.getString(it.id)
            } catch (ex: Resources.NotFoundException) {
                null
            }
        }
    }

}