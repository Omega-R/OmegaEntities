package com.omega_r.libs.entities.files.uri

import android.net.Uri
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.OmegaFileProcessor
import com.omega_r.libs.entities.files.OmegaFileProcessorsHolder
import com.omega_r.libs.entities.files.Type
import com.omega_r.libs.entities.resources.OmegaResourceExtractor
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.core.Output
import io.ktor.utils.io.streams.asInput
import io.ktor.utils.io.streams.asOutput
import java.io.FileNotFoundException
import java.util.*

class OmegaUriFile(val uri: Uri, name: String?, mimeType: String?) : OmegaFile {

    companion object {
        init {
            OmegaFileProcessorsHolder.Default.addProcessor(OmegaUriFile::class, Processor())
        }
    }

    override val type: Type = Type.FILE

    override val mimeType: String = mimeType ?: MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            ?.apply { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.US)) } ?: "*/*"

    override val name: String = name ?: URLUtil.guessFileName(uri.toString(), null, mimeType)

    constructor(uri: String, name: String?, mimeType: String?) : this(Uri.parse(uri), name, mimeType)

    class Processor : OmegaFileProcessor<OmegaUriFile> {

        override suspend fun isExist(entity: OmegaUriFile, extractor: OmegaResourceExtractor): Boolean? {
            return try {
                val stream = extractor.contentResolver?.openInputStream(entity.uri)
                if (stream == null) {
                    false
                } else {
                    stream.use { /* nothing */ }
                    true
                }
            } catch (exc: FileNotFoundException) {
                false
            }
        }

        override suspend fun getInput(entity: OmegaUriFile, extractor: OmegaResourceExtractor): Input? {
            return try {
                extractor.contentResolver?.openInputStream(entity.uri)?.asInput()
            } catch (exc: FileNotFoundException) {
                null
            }
        }

        override suspend fun getOutput(entity: OmegaUriFile, extractor: OmegaResourceExtractor): Output? {
            return try {
                extractor.contentResolver?.openOutputStream(entity.uri)?.asOutput()
            } catch (exc: FileNotFoundException) {
                null
            }
        }
    }

}

@Suppress("unused")
fun OmegaFile.Companion.from(uri: Uri, name: String? = null, mimeType: String? = null): OmegaFile = OmegaUriFile(uri, name, mimeType)