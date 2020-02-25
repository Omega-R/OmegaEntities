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

    override fun I.applyImage(imageView: ImageView, placeholderResId: Int, extractor: OmegaResourceExtractor) {
        val newPlaceholderResId = imageView.getDefaultPlaceholderResId(placeholderResId)
        applyImageInner(imageView, newPlaceholderResId, extractor)
    }

    override fun I.applyBackground(view: View, placeholderResId: Int, extractor: OmegaResourceExtractor) {
        val newPlaceholderResId = view.getDefaultPlaceholderResId(placeholderResId)
        applyBackgroundInner(view, newPlaceholderResId, extractor)
    }

    protected abstract fun I.applyImageInner(
            imageView: ImageView,
            placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
            extractor: OmegaResourceExtractor
    )

    protected abstract fun I.applyBackgroundInner(
            view: View,
            placeholderResId: Int = OmegaImage.NO_PLACEHOLDER_RES,
            extractor: OmegaResourceExtractor
    )

    protected open fun View.getDefaultPlaceholderResId(placeholderResId: Int): Int {
        return if (placeholderResId != OmegaImage.NO_PLACEHOLDER_RES) {
            placeholderResId
        } else {
            0
            // TODO
//            TypedValue().run {
//                if (context.theme.resolveAttribute(R.attr.omegaTypePlaceholderDefault, this, true)) data else {
//                    OmegaImage.NO_PLACEHOLDER_RES
//                }
//            }
        }
    }

    override suspend fun I.input(extractor: OmegaResourceExtractor, format: Format, quality: Int): Input? =
            getInputStream(extractor, format, quality)?.asInput()

    protected abstract suspend fun I.getInputStream(extractor: OmegaResourceExtractor, format: Format, quality: Int): InputStream?

    override fun I.preload(extractor: OmegaResourceExtractor) {
        // nothing
    }

}