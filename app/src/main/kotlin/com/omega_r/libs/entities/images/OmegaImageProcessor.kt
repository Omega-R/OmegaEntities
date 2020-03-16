package com.omega_r.libs.entities.images

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import kotlinx.coroutines.CoroutineScope

@SuppressLint("ObsoleteSdkInt")
actual interface OmegaImageProcessor<T : OmegaImage> : OmegaProcessor<T>, CoroutineScope {

    companion object {

        @Suppress("DEPRECATION")
        fun applyBackground(view: View, background: Drawable?) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(background)
            } else {
                view.background = background
            }
        }

        fun applyCompoundDrawable(textView: TextView, drawable: Drawable?, index: Int) {
            val drawables = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textView.compoundDrawablesRelative
            } else {
                textView.compoundDrawables
            }
            drawables[index] = drawable
            applyCompoundDrawables(textView, drawables)
        }

        fun applyCompoundDrawables(textView: TextView, drawables: Array<Drawable?>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3])
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3])
            }
        }

    }

    actual suspend fun getInput(
            image: T,
            extractor: OmegaResourceExtractor,
            format: OmegaImage.Format,
            quality: Int
    ): Input?

    fun applyImage(
            image: T,
            imageView: ImageView,
            holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun applyBackground(
            image: T,
            view: View,
            holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun applyCompoundImage(
            image: T,
            index: Int,
            textView: TextView,
            holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
            extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    )

    fun preload(image: T, extractor: OmegaResourceExtractor)

}