package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.extensions.asInput
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.*
import io.ktor.http.Url
import io.ktor.util.*
import io.ktor.utils.io.core.Input

data class OmegaUrlImage(val url: String) : OmegaImage {

    abstract class Processor constructor(private val client: HttpClient) : OmegaImageProcessor<OmegaUrlImage> {

        constructor() : this(HttpClient { /* nothing */ })

        override suspend fun getInput(
                entity: OmegaUrlImage,
                extractor: OmegaResourceExtractor,
                format: OmegaImage.Format,
                quality: Int
        ): Input? = client.request(Url(entity.url)) {
            /* nothing */
        }.readBytes().asInput()

    }

}