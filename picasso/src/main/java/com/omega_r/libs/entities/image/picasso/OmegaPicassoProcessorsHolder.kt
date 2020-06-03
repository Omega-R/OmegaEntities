package com.omega_r.libs.entities.image.picasso

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.images.*
import com.omega_r.libs.entities.images.OmegaImage.Format
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

        private val targetMap = mutableMapOf<Int, Target>()
        private val targetList = mutableListOf<Target>() // for Input

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
                        is OmegaResourceImage -> if (placeholderImage.resource.id == OmegaImage.NO_PLACEHOLDER_RES) {
                            request
                        } else {
                            request?.placeholder(placeholderImage.resource.id)
                        }
                        is OmegaDrawableImage -> request?.placeholder(placeholderImage.drawable)
                        else -> null
                    }
                }
                else -> null
            }
        }


        override fun applyImage(
                image: OmegaImage,
                imageView: ImageView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            createRequestCreator(image)?.apply {
                fit()
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (imageView.scaleType) {
                    ImageView.ScaleType.FIT_CENTER,
                    ImageView.ScaleType.CENTER_INSIDE -> centerInside()
                    ImageView.ScaleType.CENTER_CROP -> centerCrop()
                }
                into(imageView)
            } ?: defaultHolder.getProcessor(image).applyImage(image, imageView, holder, extractor)
        }

        override fun applyBackground(
                image: OmegaImage,
                view: View,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            createRequestCreator(image)?.apply {
                if (view.width <= 0 || view.height <= 0) {
                    ImageSizeExtractor(view) {
                        applyBackground(image, view, holder, extractor)
                    }
                } else {
                    resize(view.width, view.height)
                    into(object : DisposableTarget<View>(view) {
                        override fun onPrepareLoad(view: View, placeHolderDrawable: Drawable?) {
                            OmegaImageProcessor.applyBackground(view, placeHolderDrawable)
                        }

                        override fun onBitmapFailed(view: View, errorDrawable: Drawable?) {
                            OmegaImageProcessor.applyBackground(view, errorDrawable)
                        }

                        override fun onBitmapLoaded(view: View, bitmap: Bitmap?) {
                            OmegaImageProcessor.applyBackground(view, bitmap.toDrawable(view.resources))
                        }
                    })
                }
            } ?: defaultHolder.getProcessor(image).applyBackground(image, view, holder, extractor)
        }

        override fun applyCompoundImage(
                image: OmegaImage,
                index: Int,
                textView: TextView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            createRequestCreator(image)?.run {
                into(object : DisposableTarget<TextView>(textView) {
                    override fun onPrepareLoad(view: TextView, placeHolderDrawable: Drawable?) {
                        OmegaImageProcessor.applyCompoundDrawable(view, placeHolderDrawable, index)
                    }

                    override fun onBitmapFailed(view: TextView, errorDrawable: Drawable?) {
                        OmegaImageProcessor.applyCompoundDrawable(view, errorDrawable, index)
                    }

                    override fun onBitmapLoaded(view: TextView, bitmap: Bitmap?) {
                        OmegaImageProcessor.applyCompoundDrawable(view, bitmap.toDrawable(view.resources), index)
                    }
                })
            }
        }

        override suspend fun getInput(image: OmegaImage, extractor: OmegaResourceExtractor, format: Format, quality: Int): Input? {
            return createRequestCreator(image)?.run {
                withContext(Dispatchers.Main) {
                    val stream = WrapperInputStream()
                    val target = object : Target {
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            // stream can only send data once
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            stream.inputStream = null
                            targetList.remove(this)
                        }

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            stream.inputStream = bitmap?.toInputStream(format, quality)
                            targetList.remove(this)
                        }
                    }
                    targetList.add(target)
                    into(target)
                    stream.asInput()
                }
            } ?: defaultHolder.getProcessor(image).getInput(image, extractor, format, quality)
        }

        override fun preload(image: OmegaImage, extractor: OmegaResourceExtractor) {
            createRequestCreator(image)?.fetch() ?: defaultHolder.getProcessor(image).preload(image, extractor)
        }

        private fun Bitmap?.toDrawable(resources: Resources): Drawable? = this?.let { BitmapDrawable(resources, this) }

        private abstract inner class DisposableTarget<T : View>(view: T) : Target {

            private val weakViewReference = WeakReference<T>(view)

            init {
                targetMap[view.id]?.let {
                    Picasso.get().cancelRequest(it)
                    targetMap.remove(view.id)
                }
                targetMap[view.id] = this
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                weakViewReference.get()?.let { view ->
                    onPrepareLoad(view, placeHolderDrawable)
                }
            }

            abstract fun onPrepareLoad(view: T, placeHolderDrawable: Drawable?)

            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                weakViewReference.get()?.let { view ->
                    onBitmapFailed(view, errorDrawable)
                    targetMap.remove(view.id)
                }
            }

            abstract fun onBitmapFailed(view: T, errorDrawable: Drawable?)

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                weakViewReference.get()?.let { view ->
                    onBitmapLoaded(view, bitmap)
                    targetMap.remove(view.id)
                }
            }

            abstract fun onBitmapLoaded(view: T, bitmap: Bitmap?)

        }

    }

}