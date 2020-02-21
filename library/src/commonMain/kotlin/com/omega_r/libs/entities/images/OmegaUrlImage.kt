package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.extensions.asInput
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.http.Url
import io.ktor.utils.io.core.Input

data class OmegaUrlImage(val url: String) : OmegaImage {

    companion object {
        init {
            OmegaImageProcessorsHolder.Default.addProcessor(OmegaUrlImage::class, OmegaUrlImageProcessor())
        }
    }

}

class OmegaUrlImageProcessor constructor(private val client: HttpClient) : OmegaImageProcessor<OmegaUrlImage> {

    constructor() : this(HttpClient { /* nothing */ })

    override suspend fun OmegaUrlImage.input(): Input? = client.request<ByteArray>(Url(url)) {
        /* nothing */
    }.asInput()

}