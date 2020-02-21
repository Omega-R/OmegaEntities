package com.omega_r.libs.entities.images

import android.view.View
import android.widget.ImageView
import com.omega_r.libs.entities.processors.OmegaProcessor
import io.ktor.utils.io.core.Input

actual interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor {

    actual suspend fun T.input(): Input?

    fun T.applyImage(imageView: ImageView, placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES)

    fun T.applyBackground(view: View, placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES)

}

val OmegaImage.Companion.NO_PLACEHOLDER_RES: Int
    get() = 0