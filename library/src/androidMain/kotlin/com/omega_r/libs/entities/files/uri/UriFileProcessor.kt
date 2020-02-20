package com.omega_r.libs.entities.files.uri

import android.content.ContentResolver
import android.content.Context
import com.omega_r.libs.entities.files.OmegaFileProcessor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output
import io.ktor.utils.io.streams.asInput
import io.ktor.utils.io.streams.asOutput
import java.io.FileNotFoundException

class UriFileProcessor(private val contentResolver: ContentResolver) : OmegaFileProcessor<OmegaUriFile> {

    constructor(context: Context) : this(context.contentResolver)

    override suspend fun OmegaUriFile.exist(): Boolean? {
        return try {
            contentResolver.openInputStream(uri).use { /* nothing */ }
            true
        } catch (exc: FileNotFoundException) {
            false
        }
    }

    override suspend fun OmegaUriFile.input(): Input? {
        return try {
            contentResolver.openInputStream(uri).asInput()
        } catch (exc: FileNotFoundException) {
            null
        }
    }

    override suspend fun OmegaUriFile.output(): Output? {
        return try {
            contentResolver.openOutputStream(uri).asOutput()
        } catch (exc: FileNotFoundException) {
            null
        }
    }
}