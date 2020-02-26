package com.omega_r.libs.entities.images

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import com.omega_r.libs.entities.decoders.BitmapDecoders
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.images.OmegaImageProcessor.Companion.applyBackground
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.tools.ImageAsyncExecutor.Companion.executeImageAsync
import com.omega_r.libs.entities.tools.ImageSizeExtractor
import com.omega_r.libs.entities.tools.getScaledBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.ref.WeakReference

abstract class BaseBitmapImage : OmegaImage {

    abstract class Processor<B : BaseBitmapImage>(private val autoRecycle: Boolean) : OmegaBaseImageProcessor<B>(), OmegaImageProcessor<B> {

        protected abstract suspend fun getBitmap(
                entity: B,
                extractor: OmegaResourceExtractor,
                width: Int? = null,
                height: Int? = null
        ): Bitmap?

        override fun applyImageInner(entity: B, imageView: ImageView, placeholderResId: Int, extractor: OmegaResourceExtractor) {
            if (placeholderResId == OmegaImage.NO_PLACEHOLDER_RES) {
                imageView.setImageDrawable(null)
            } else {
                imageView.setImageResource(placeholderResId)
            }

            val width = imageView.width
            val height = imageView.height

            if (width <= 0 || height <= 0) {
                ImageSizeExtractor(imageView) { target ->
                    applyImageInner(entity, target, placeholderResId, extractor)
                }
            } else {
                val imageScaleType = imageView.scaleType
                executeImageAsync(imageView, {
                    getBitmap(entity, extractor, width, height)?.run {
                        getScaledBitmap(width, height, imageScaleType, autoRecycle, this)
                    }
                }, ImageView::setImageBitmap)
            }
        }

        override fun applyBackgroundInner(entity: B, view: View, placeholderResId: Int, extractor: OmegaResourceExtractor) {
            if (placeholderResId == OmegaImage.NO_PLACEHOLDER_RES) {
                view.background = null
            } else {
                view.setBackgroundResource(placeholderResId)
            }

            val viewWeak = WeakReference(view)
            val processor = OmegaImageProcessorsHolder.current.getProcessor(entity)
            processor.launch {
                val bitmap = getBitmap(entity, extractor)
                withContext(Dispatchers.Main) {
                    viewWeak.get()?.let { view ->
                        applyBackground(view, bitmap?.let { BitmapDrawable(view.resources, it) })
                    }
                }
            }
        }

        override suspend fun getInputStream(entity: B, extractor: OmegaResourceExtractor, format: OmegaImage.Format, quality: Int): InputStream? {
            val bitmap = getBitmap(entity, extractor)
            try {
                return bitmap.toInputStream(format, quality)
            } finally {
                if (autoRecycle && bitmap != null) {
                    BitmapDecoders.current.recycle(bitmap)
                }
            }
        }
    }

}