package com.omega_r.libs.entities.images

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.CoroutineScope
import java.io.InputStream

actual interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor<T>, CoroutineScope {

    companion object {

        @Suppress("DEPRECATION")
        @SuppressLint("ObsoleteSdkInt")
        fun applyBackground(view: View, background: Drawable?) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(background)
            } else {
                view.background = background
            }
        }

        fun applyEmptyBackground(view: View, placeholderResId: Int) {
            if (placeholderResId != OmegaImage.NO_PLACEHOLDER_RES) {
                view.setBackgroundResource(placeholderResId)
            } else {
                applyBackground(view, null)
            }
        }

    }

    actual suspend fun T.input(
            extractor: OmegaResourceExtractor,
            format: OmegaImage.Format,
            quality: Int
    ): Input?

    fun T.applyImage(
            imageView: ImageView,
            placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun T.applyBackground(
            view: View,
            placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun T.preload(extractor: OmegaResourceExtractor)

}