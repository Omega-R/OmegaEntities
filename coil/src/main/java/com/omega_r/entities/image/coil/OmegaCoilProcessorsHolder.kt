package com.omega_r.entities.image.coil

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.Coil
import coil.api.newGetBuilder
import coil.api.newLoadBuilder
import coil.request.GetRequestBuilder
import coil.request.LoadRequestBuilder
import coil.target.Target
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.extensions.toBitmapAndRecycle
import com.omega_r.libs.entities.extensions.toInputStream
import com.omega_r.libs.entities.images.*
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

class OmegaCoilProcessorsHolder(
    private val defaultHolder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.Default,
    private vararg val excludeImageClasses: KClass<out OmegaImage>
) : OmegaImageProcessorsHolder {

    companion object {

        fun setAsCurrentImagesProcessor(
            defaultHolder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current,
            vararg excludeImageClasses: KClass<out OmegaImage>
        ) {
            OmegaImageProcessorsHolder.current = OmegaCoilProcessorsHolder(defaultHolder, *excludeImageClasses)
        }

    }

    private val processor = Processor()

    override fun getProcessor(entity: OmegaImage): OmegaImageProcessor<OmegaImage> {
        return if (excludeImageClasses.contains(entity::class)) defaultHolder.getProcessor(entity) else processor
    }

    private fun createLoadRequestBuilder(context: Context, image: OmegaImage): LoadRequestBuilder? {
        if (excludeImageClasses.contains(image::class)) return null

        val requestBuilder = Coil.loader().newLoadBuilder(context)
        return when (image) {
            is OmegaUrlImage -> requestBuilder.data(HttpUrl.get(image.url))
            is OmegaUriImage -> requestBuilder.data(image.uri)
            is OmegaJavaFileImage -> requestBuilder.data(image.file)
            is OmegaResourceImage -> requestBuilder.data(image.resource.id)
            is OmegaDrawableImage -> requestBuilder.data(image.drawable)
            is OmegaBitmapImage -> requestBuilder.data(image.bitmap)
            is OmegaPlaceholderImage -> {
                val finalImageRequestBuilder = createLoadRequestBuilder(context, image.finalImage)
                when (val placeholderImage = image.placeholderImage) {
                    is OmegaResourceImage -> {
                        if (placeholderImage.resource.id != OmegaImage.NO_PLACEHOLDER_RES) {
                            finalImageRequestBuilder?.placeholder(placeholderImage.resource.id)
                        }
                    }
                    is OmegaDrawableImage -> finalImageRequestBuilder?.placeholder(placeholderImage.drawable)
                }
                finalImageRequestBuilder
            }
            else -> null
        }
    }

    private fun createGetRequestBuilder(image: OmegaImage): GetRequestBuilder? {
        if (excludeImageClasses.contains(image::class)) return null

        val requestBuilder = Coil.loader().newGetBuilder()
        return when (image) {
            is OmegaUrlImage -> requestBuilder.data(HttpUrl.get(image.url))
            is OmegaUriImage -> requestBuilder.data(image.uri)
            is OmegaJavaFileImage -> requestBuilder.data(image.file)
            is OmegaResourceImage -> requestBuilder.data(image.resource.id)
            is OmegaDrawableImage -> requestBuilder.data(image.drawable)
            is OmegaBitmapImage -> requestBuilder.data(image.bitmap)
            is OmegaPlaceholderImage -> createGetRequestBuilder(image.finalImage)
            else -> null
        }
    }

    private inner class Processor : OmegaImageProcessor<OmegaImage>, CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Default

        override fun applyBackground(
            image: OmegaImage,
            view: View,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            createLoadRequestBuilder(view.context, image)
                ?.target(object : Target {
                    override fun onError(error: Drawable?) {
                        error?.let { OmegaImageProcessor.applyBackground(view, it) }
                    }

                    override fun onStart(placeholder: Drawable?) {
                        placeholder?.let { OmegaImageProcessor.applyBackground(view, it) }
                    }

                    override fun onSuccess(result: Drawable) {
                        OmegaImageProcessor.applyBackground(view, result)
                    }
                })
                ?.build()
                ?.let {
                    Coil.loader().load(it)
                } ?: defaultHolder.getProcessor(image).applyBackground(image, view, holder, extractor)
        }

        override fun applyCompoundImage(
            image: OmegaImage,
            index: Int,
            textView: TextView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            createLoadRequestBuilder(textView.context, image)
                ?.target(object : Target {
                    override fun onError(error: Drawable?) {
                        OmegaImageProcessor.applyCompoundDrawable(textView, error, index)
                    }

                    override fun onStart(placeholder: Drawable?) {
                        OmegaImageProcessor.applyCompoundDrawable(textView, placeholder, index)
                    }

                    override fun onSuccess(result: Drawable) {
                        OmegaImageProcessor.applyCompoundDrawable(textView, result, index)
                    }
                })
                ?.build()
                ?.let {
                    Coil.loader().load(it)
                } ?: defaultHolder.getProcessor(image).applyCompoundImage(image, index, textView, holder, extractor)
        }

        override fun applyImage(
            image: OmegaImage,
            imageView: ImageView,
            holder: OmegaImageProcessorsHolder,
            extractor: OmegaResourceExtractor
        ) {
            createLoadRequestBuilder(imageView.context, image)
                ?.target(imageView)
                ?.build()
                ?.let {
                    Coil.loader().load(it)
                } ?: defaultHolder.getProcessor(image).applyImage(image, imageView, holder, extractor)
        }

        override suspend fun getInput(
            image: OmegaImage,
            extractor: OmegaResourceExtractor,
            format: OmegaImage.Format,
            quality: Int
        ): Input? {
            return extractor.context?.let { context ->
                createGetRequestBuilder(image)?.let {
                    Coil.loader()
                        .get(it.build())
                        .toBitmapAndRecycle {
                            toInputStream(format = OmegaImage.Format.JPEG).asInput()
                        }
                } ?: defaultHolder.getProcessor(image).getInput(image, extractor, format, quality)
            } ?: defaultHolder.getProcessor(image).getInput(image, extractor, format, quality)
        }

        override fun preload(image: OmegaImage, extractor: OmegaResourceExtractor) {
            extractor.context?.let { context ->
                createLoadRequestBuilder(context, image)
                    ?.build()
                    ?.let {
                        Coil.loader().load(it)
                    } ?: defaultHolder.getProcessor(image).preload(image, extractor)
            } ?: defaultHolder.getProcessor(image).preload(image, extractor)
        }

    }

}