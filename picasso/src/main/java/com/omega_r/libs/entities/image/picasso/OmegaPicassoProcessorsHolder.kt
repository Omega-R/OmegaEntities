package com.omega_r.libs.entities.image.picasso

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.images.*
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import com.omega_r.libs.entities.tools.ImageSizeExtractor
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

class OmegaPicassoProcessorsHolder(
        picasso: Picasso? = null,
        private val defaultHolder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
        private vararg val excludeImageClasses: KClass<out OmegaImage>
) : OmegaImageProcessorsHolder {

    companion object {
        fun setAsCurrentImagesProcessor(
                picasso: Picasso? = null,
                defaultHolder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current,
                vararg excludeImageClasses: KClass<out OmegaImage>
        ) {
            OmegaImageProcessorsHolder.current = OmegaPicassoProcessorsHolder(picasso, defaultHolder, *excludeImageClasses)
        }
    }

    private val picasso: Picasso by lazy { picasso ?: Picasso.get() }

    private val processor = Processor()

    override fun getProcessor(entity: OmegaImage): OmegaImageProcessor<OmegaImage> {
        return if (excludeImageClasses.contains(entity::class)) defaultHolder.getProcessor(entity) else processor
    }

    private inner class Processor : OmegaImageProcessor<OmegaImage>, CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        private fun createRequestCreator(image: OmegaImage): RequestCreator? {
            if (excludeImageClasses.contains(image::class)) return null

            return when (image) {
                is OmegaUrlImage -> picasso.load(image.url)
                is OmegaUriImage -> picasso.load(image.uri)
                is OmegaJavaFileImage -> picasso.load(image.file)
                is OmegaResourceImage -> picasso.load(image.resource.id)
                is OmegaPlaceholderImage -> {
                    val request = createRequestCreator(image.finalImage)
                    when (val placeholderImage = image.placeholderImage) {
                        is OmegaResourceImage -> if(placeholderImage.resource.id == OmegaImage.NO_PLACEHOLDER_RES) request else request?.placeholder(placeholderImage.resource.id)
                        is OmegaDrawableImage -> request?.placeholder(placeholderImage.drawable)
                        else -> null
                    }
                }
                else -> null
            }
        }


        override fun applyImage(entity: OmegaImage, imageView: ImageView, holder: OmegaImageProcessorsHolder, extractor: OmegaResourceExtractor) {
            createRequestCreator(entity)?.apply {
                fit()
                when (imageView.scaleType) {
                    ImageView.ScaleType.FIT_CENTER,
                    ImageView.ScaleType.CENTER_INSIDE -> centerInside()
                    ImageView.ScaleType.CENTER_CROP -> centerCrop()
                    else -> { }
                }
                into(imageView)
            } ?: defaultHolder.getProcessor(entity).applyImage(entity, imageView, holder, extractor)
        }

        override fun applyBackground(
                entity: OmegaImage,
                view: View,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            createRequestCreator(entity)?.apply {
                if (view.width <= 0 || view.height <= 0) {
                    ImageSizeExtractor(view) {
                        applyBackground(entity, view, holder, extractor)
                    }
                } else {
                    resize(view.width, view.height)
                    val viewWeak = WeakReference(view)
                    into(object : Target {
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            viewWeak.get()?.let {
                                OmegaImageProcessor.applyBackground(view, placeHolderDrawable)
                            }
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            viewWeak.get()?.let {
                                OmegaImageProcessor.applyBackground(view, errorDrawable)
                            }
                        }

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            viewWeak.get()?.let {
                                OmegaImageProcessor.applyBackground(view, BitmapDrawable(it.resources, bitmap))
                            }
                        }
                    })
                }
            } ?: defaultHolder.getProcessor(entity).applyBackground(entity, view, holder, extractor)
        }

        override fun applyCompoundImage(entity: OmegaImage, index: Int, textView: TextView, holder: OmegaImageProcessorsHolder, extractor: OmegaResourceExtractor) {
            createRequestCreator(entity)?.run {
                into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        OmegaImageProcessor.applyCompoundDrawable(textView, placeHolderDrawable, index)
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        OmegaImageProcessor.applyCompoundDrawable(textView, errorDrawable, index)
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        OmegaImageProcessor.applyCompoundDrawable(
                                textView,
                                bitmap?.let { BitmapDrawable(textView.resources, bitmap) },
                                index
                        )
                    }
                })
            }
        }

        override suspend fun getInput(
                entity: OmegaImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): Input? {
            return createRequestCreator(entity)?.run {
                withContext(Dispatchers.Main) {
                    val stream = WrapperInputStream()
                    into(object : Target {
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            // stream can only send data once
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            stream.inputStream = null
                        }

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            if (bitmap == null) {
                                stream.inputStream = null
                            } else {
                                stream.inputStream = bitmap.toInputStream(format, quality)
                            }
                        }
                    })
                    stream.asInput()
                }
            } ?: defaultHolder.getProcessor(entity).getInput(entity, extractor, format, quality)
        }

        override fun preload(entity: OmegaImage, extractor: OmegaResourceExtractor) {
            createRequestCreator(entity)?.fetch() ?: defaultHolder.getProcessor(entity).preload(entity, extractor)
        }

    }

}