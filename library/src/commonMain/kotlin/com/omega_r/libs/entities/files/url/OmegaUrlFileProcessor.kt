package com.omega_r.libs.entities.files.url

import com.omega_r.libs.entities.extensions.asInput
import com.omega_r.libs.entities.files.OmegaFileProcessor
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.isSuccess
import io.ktor.util.KtorExperimentalAPI
import io.ktor.utils.io.core.Input

class OmegaUrlFileProcessor constructor(private val client: HttpClient) : OmegaFileProcessor<OmegaUrlFile> {

    constructor() : this(HttpClient { /* nothing */ })

    override suspend fun OmegaUrlFile.exist(): Boolean? = client.request<HttpResponse>(Url(url)) {
        method = HttpMethod.Head
    }.status.isSuccess()

    @KtorExperimentalAPI
    override suspend fun OmegaUrlFile.input(): Input? = client.request<ByteArray>(Url(url)) {
        /* nothing */
    }.asInput()

}