package com.omega_r.libs.entities.images

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import java.io.InputStream

abstract class OmegaBaseImageProcessor<I : OmegaImage> : OmegaImageProcessor<I> {

    override fun I.applyImage(imageView: ImageView, placeholderResId: Int) {
        val newPlaceholderResId = imageView.getDefaultPlaceholderResId(placeholderResId)
        applyImageInner(imageView, newPlaceholderResId)
    }

    override fun I.applyBackground(view: View, placeholderResId: Int) {
        val newPlaceholderResId = view.getDefaultPlaceholderResId(placeholderResId)
        applyBackgroundInner(view, newPlaceholderResId)
    }

    abstract suspend fun I.getStream(context: Context, compressFormat: CompressFormat = JPEG, quality: Int = 100): InputStream?

    open fun I.preload(context: Context) {
        // nothing
    }

    protected abstract fun I.applyImageInner(imageView: ImageView, placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES)

    protected abstract fun I.applyBackgroundInner(view: View, placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES)

    protected open fun View.getDefaultPlaceholderResId(placeholderResId: Int): Int {
        return if (placeholderResId != OmegaImage.NO_PLACEHOLDER_RES) {
            placeholderResId
        } else {
            TypedValue().run {
                0
                //                if (context.theme.resolveAttribute(R.attr.omegaTypePlaceholderDefault, this, true)) data else {
//                    NO_PLACEHOLDER_RES
//                }
            }
        }
    }

}