package com.omega_r.libs.entities.files.url

import com.omega_r.libs.entities.extensions.asInput
import com.omega_r.libs.entities.files.OmegaFileProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.isSuccess
import io.ktor.utils.io.core.Input

class OmegaUrlFileProcessor constructor(private val client: HttpClient) : OmegaFileProcessor<OmegaUrlFile> {

    constructor() : this(HttpClient { /* nothing */ })

    override suspend fun isExist(entity: OmegaUrlFile, extractor: OmegaResourceExtractor): Boolean? = client.request<HttpResponse>(Url(entity.url)) {
        method = HttpMethod.Head
    }.status.isSuccess()

    override suspend fun getInput(entity: OmegaUrlFile, extractor: OmegaResourceExtractor): Input? = client.request<ByteArray>(Url(entity.url)) {
        /* nothing */
    }.asInput()

}