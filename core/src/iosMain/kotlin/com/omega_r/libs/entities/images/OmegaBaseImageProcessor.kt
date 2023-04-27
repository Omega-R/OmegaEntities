package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class OmegaBaseImageProcessor<I: OmegaImage>: OmegaImageProcessor<I> {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    override suspend fun getInput(entity: I, extractor: OmegaResourceExtractor, format: OmegaImage.Format, quality: Int): Input? =
        getInputStream(entity, extractor, format, quality)?.asInput()

    protected abstract suspend fun getInputStream(
        entity: I,
        extractor: OmegaResourceExtractor,
        format: OmegaImage.Format,
        quality: Int
    ): InputStream?

    override fun preload(entity: I, extractor: OmegaResourceExtractor) {
        // nothing
    }


}