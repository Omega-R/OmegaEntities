package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input

interface OmegaImage : OmegaEntity {

    companion object {

        fun from(url: String): OmegaImage = OmegaUrlImage(url)

        fun from(resource: OmegaResource.Image): OmegaImage = OmegaResourceImage(resource)

    }

    suspend fun getInput(
            holder: OmegaImageProcessorsHolder = OmegaImageProcessorsHolder.current,
            extractor: OmegaResourceExtractor,
            format: Format = Format.JPEG,
            quality: Int = 100
    ): Input? = with(holder.getProcessor(this)) {
        input(extractor, format, quality)
    }

    enum class Format {

        JPEG,
        PNG,
        WEBP

    }

}