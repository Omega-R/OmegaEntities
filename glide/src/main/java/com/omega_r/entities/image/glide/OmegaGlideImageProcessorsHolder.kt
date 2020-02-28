package com.omega_r.entities.image.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.omega_r.libs.entities.decoders.BitmapDecoders
import com.omega_r.libs.entities.decoders.SimpleBitmapDecoders
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.images.*
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass
import io.ktor.utils.io.core.Input

class OmegaGlideImageProcessorsHolder(
        private val defaultHolder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
        private vararg val excludeImageClasses: KClass<out OmegaImage>
) : OmegaImageProcessorsHolder {

    companion object {

        fun setAsCurrentImagesProcessor(
                defaultHolder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current,
                vararg excludeImageClasses: KClass<out OmegaImage>
        ) {
            OmegaImageProcessorsHolder.current = OmegaGlideImageProcessorsHolder(defaultHolder, *excludeImageClasses)
        }

        fun setGlideBitmapPool(context: Context) {
            BitmapDecoders.current = SimpleBitmapDecoders(GlideBitmapPool(Glide.get(context).bitmapPool))
        }

    }

    private val processor = Processor()

    override fun getProcessor(entity: OmegaImage): OmegaImageProcessor<OmegaImage> {
        return if (excludeImageClasses.contains(entity::class)) defaultHolder.getProcessor(entity) else processor
    }

    private inner class Processor : OmegaImageProcessor<OmegaImage>, CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        private fun <T> RequestBuilder<T>.createRequestBuilder(
                image: OmegaImage,
                extractor: OmegaResourceExtractor
        ): RequestBuilder<T>? {
            if (excludeImageClasses.contains(image::class)) return null

            return when (image) {
                is OmegaUrlImage -> load(image.url)
                is OmegaUriImage -> load(image.uri)
                is OmegaJavaFileImage -> load(image.file)
                is OmegaResourceImage -> load(image.resource.id)
                is OmegaBitmapImage -> load(image.bitmap)
                is OmegaDrawableImage -> load(image.drawable)
                is OmegaByteArrayImage -> load(image.byteArray)
                is OmegaPlaceholderImage -> {
                    when (val placeholderImage = image.placeholderImage) {
                        is OmegaUrlImage -> thumbnail(load(placeholderImage.url))
                                .load(createRequestBuilder(image.finalImage, extractor))
                        is OmegaResourceImage -> {
                            if (placeholderImage.resource.id == OmegaImage.NO_PLACEHOLDER_RES) {
                                createRequestBuilder(image.finalImage, extractor)
                            } else {
                                placeholder(placeholderImage.resource.id)
                                        .load(createRequestBuilder(image.finalImage, extractor))
                            }
                        }
                        is OmegaDrawableImage -> placeholder(placeholderImage.drawable)
                                .load(createRequestBuilder(image.finalImage, extractor))
                        is OmegaBitmapImage, is OmegaUriImage, is OmegaJavaFileImage, is OmegaByteArrayImage -> {
                            thumbnail(createRequestBuilder(image.placeholderImage, extractor))
                                    .load(createRequestBuilder(image.finalImage, extractor))
                        }
                        else -> null
                    }
                }
                else -> null
            }
        }

        override fun applyImage(
                entity: OmegaImage,
                imageView: ImageView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            Glide.with(imageView)
                    .asDrawable()
                    .createRequestBuilder(entity, extractor)
                    ?.into(imageView)
                    ?: defaultHolder.getProcessor(entity).applyImage(entity, imageView, holder, extractor)
        }

        override fun applyBackground(
                entity: OmegaImage,
                view: View,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            Glide.with(view)
                    .asDrawable()
                    .createRequestBuilder(entity, extractor)
                    ?.into(object : CustomViewTarget<View, Drawable>(view) {

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            OmegaImageProcessor.applyBackground(view, errorDrawable)
                        }

                        override fun onResourceCleared(placeholder: Drawable?) {
                            OmegaImageProcessor.applyBackground(view, placeholder)
                        }

                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            OmegaImageProcessor.applyBackground(view, resource)
                        }

                    }) ?: defaultHolder.getProcessor(entity).applyBackground(entity, view, holder, extractor)
        }

        override fun applyCompoundImage(
                entity: OmegaImage,
                index: Int,
                textView: TextView,
                holder: OmegaImageProcessorsHolder,
                extractor: OmegaResourceExtractor
        ) {
            extractor.context?.let { context ->
                Glide.with(context)
                        .asDrawable()
                        .createRequestBuilder(entity, extractor)
                        ?.run {
                            val futureTarget = submit()
                            try {
                                val drawable = futureTarget.get()
                                OmegaImageProcessor.applyCompoundDrawable(textView, drawable, index)
                            } finally {
                                Glide.with(context)
                                        .clear(futureTarget)
                            }
                        } ?: defaultHolder.getProcessor(entity).applyCompoundImage(entity, index, textView, holder, extractor)
            }  ?: defaultHolder.getProcessor(entity).applyCompoundImage(entity, index, textView, holder, extractor)
        }

        override suspend fun getInput(
                entity: OmegaImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): Input? {
            return extractor.context?.let { context ->
                Glide.with(context)
                        .asBitmap()
                        .createRequestBuilder(entity, extractor)
                        ?.run {
                            val futureTarget = submit()
                            try {
                                val bitmap = futureTarget.get()
                                bitmap.toInputStream(format, quality).asInput()
                            } finally {
                                Glide.with(context)
                                        .clear(futureTarget)
                            }
                        }
            } ?: defaultHolder.getProcessor(entity).getInput(entity, extractor, format, quality)
        }

        override fun preload(
                entity: OmegaImage,
                extractor: OmegaResourceExtractor
        ) {
            extractor.context?.let {
                Glide.with(it)
                        .asDrawable()
                        .createRequestBuilder(entity, extractor)
                        ?.preload()
                        ?: defaultHolder.getProcessor(entity).preload(entity, extractor)
            } ?: defaultHolder.getProcessor(entity).preload(entity, extractor)
        }

    }

}