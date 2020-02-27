package com.omega_r.libs.entities.images

import android.graphics.drawable.Drawable
import android.widget.TextView
import com.omega_r.libs.entities.images.OmegaImage.Format
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.Dispatchers
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

abstract class OmegaBaseImageProcessor<I : OmegaImage> : OmegaImageProcessor<I> {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

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