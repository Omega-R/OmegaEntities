package com.omega_r.libs.entities.files.url

import com.omega_r.libs.entities.files.OmegaFileProcessor
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.isSuccess
import kotlinx.io.core.Input
import kotlinx.io.core.Output

class OmegaUrlFileProcessor(private val client: HttpClient = HttpClient { /* nothing */ }) : OmegaFileProcessor<OmegaUrlFile> {

    override suspend fun OmegaUrlFile.exist(): Boolean? {
        val request = client.request<HttpResponse>(Url(url)) {
            method = HttpMethod.Head
        }
        return request.status.isSuccess()
    }

    override suspend fun OmegaUrlFile.input(): Input? {
        val request = client.request<HttpResponse>(Url(url)) {
            // nothing
        }
        if (request.status.isSuccess()) return OmegaInput(request.content)
        return null
    }

    override suspend fun OmegaUrlFile.output(): Output? {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }


}