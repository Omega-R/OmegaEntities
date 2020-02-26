package com.omega_r.libs.entities.images

import android.view.View
import android.widget.ImageView
import com.omega_r.libs.entities.extensions.NO_PLACEHOLDER_RES
import com.omega_r.libs.entities.images.OmegaImage.Format
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.Dispatchers
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

abstract class OmegaBaseImageProcessor<I : OmegaImage> : OmegaImageProcessor<I> {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    override fun applyImage(entity: I, imageView: ImageView, placeholderResId: Int, extractor: OmegaResourceExtractor) {
        val newPlaceholderResId = imageView.getDefaultPlaceholderResId(placeholderResId, extractor)
        applyImageInner(entity, imageView, newPlaceholderResId, extractor)
    }

    override fun applyBackground(entity: I, view: View, placeholderResId: Int, extractor: OmegaResourceExtractor) {
        val newPlaceholderResId = view.getDefaultPlaceholderResId(placeholderResId, extractor)
        applyBackgroundInner(entity, view, newPlaceholderResId, extractor)
    }

    protected abstract fun applyImageInner(
            entity: I,
            imageView: ImageView,
            placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
            extractor: OmegaResourceExtractor
    )

    protected abstract fun applyBackgroundInner(
            entity: I,
            view: View,
            placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
            extractor: OmegaResourceExtractor
    )

    protected open fun View.getDefaultPlaceholderResId(placeholderResId: Int, extractor: OmegaResourceExtractor): Int {
        return if (placeholderResId == OmegaImage.NO_PLACEHOLDER_RES) {
            OmegaImage.NO_PLACEHOLDER_RES
        } else {

//            extractor.resolveAttribute(R.attr.omegaTypePlaceholderDefault) ?: OmegaImage.NO_PLACEHOLDER_RES
            placeholderResId
        }
    }

    override suspend fun getInput(entity: I, extractor: OmegaResourceExtractor, format: Format, quality: Int): Input? =
            getInputStream(entity, extractor, format, quality)?.asInput()

    protected abstract suspend fun getInputStream(
            entity: I,
            extractor: OmegaResourceExtractor,
            format: Format,
            quality: Int
    ): InputStream?

    override fun preload(entity: I, extractor: OmegaResourceExtractor) {
        // nothing
    }

}