package com.omega_r.libs.entities.images

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import java.io.InputStream

abstract class BaseBitmapImage : OmegaImage {

    abstract class Processor<I : BaseBitmapImage>(private val autoRecycle: Boolean) : OmegaBaseImageProcessor<I>(), OmegaImageProcessor<I> {

        override fun I.applyImageInner(imageView: ImageView, placeholderResId: Int) {
            if (placeholderResId == OmegaImage.NO_PLACEHOLDER_RES) {
                imageView.setImageDrawable(null)
            } else {
                imageView.setImageResource(placeholderResId)
            }

            val width = imageView.width
            val height = imageView.height

//            if (width <= 0 || height <= 0) {
//                ImageSizeExtractor(imageView) { target ->
//                    applyImageInner(target, placeholderResId)
//                }
//            } else {
//                val imageScaleType = imageView.scaleType
//                executeImageAsync(imageView, extractor = { context ->
//                    getBitmap(context, this, width, height)?.run {
//                        getScaledBitmap(width, height, imageScaleType, autoRecycle, this)
//                    }
//                }, setter = ImageView::setImageBitmap)
//            }
        }

        protected abstract suspend fun I.getBitmap(context: Context, width: Int? = null, height: Int? = null): Bitmap?

        override fun I.applyBackgroundInner(view: View, placeholderResId: Int) {
            if (placeholderResId == OmegaImage.NO_PLACEHOLDER_RES) {
                view.background = null
            } else {
                view.setBackgroundResource(placeholderResId)
            }

//            val viewWeak = WeakReference(view)
//            ImageProcessors.current.launch {
//                val view1 = viewWeak.get() ?: return@launch
//                val bitmap = getBitmap(view1.context, this@applyBackgroundInner, null)
//                withContext(Dispatchers.Main) {
//                    val view2 = viewWeak.get() ?: return@withContext
//                    Image.Processor.applyBackground(view2, bitmap?.let { BitmapDrawable(view2.resources, it) })
//                }
//            }
        }

        override suspend fun I.getStream(context: Context, compressFormat: Bitmap.CompressFormat, quality: Int): InputStream? {
                val bitmap = getBitmap(context)
                try {
                    return bitmap.toInputStream(compressFormat, quality)
                } finally {
                    if (autoRecycle && bitmap != null) {
//                        BitmapDecoders.current.recycle(bitmap)
                    }
                }
        }

        override fun I.preload(context: Context) {
            // nothing
        }

    }

}