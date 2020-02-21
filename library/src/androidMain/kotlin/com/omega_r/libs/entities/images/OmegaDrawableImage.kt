package com.omega_r.libs.entities.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import io.ktor.utils.io.core.Input
import java.io.InputStream

data class OmegaDrawableImage(val drawable: Drawable) : OmegaImage {

    class Processor : OmegaBaseImageProcessor<OmegaDrawableImage>() {

        override suspend fun OmegaDrawableImage.getStream(context: Context, compressFormat: Bitmap.CompressFormat, quality: Int): InputStream? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun OmegaDrawableImage.applyImageInner(imageView: ImageView, placeholderResId: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun OmegaDrawableImage.applyBackgroundInner(view: View, placeholderResId: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override suspend fun OmegaDrawableImage.input(): Input? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}

fun OmegaImage.Companion.from(drawable: Drawable): OmegaImage = OmegaDrawableImage(drawable)