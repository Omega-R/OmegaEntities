package com.omega_r.libs.entities.files

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import kotlinx.io.core.Input
import kotlinx.io.core.Output
import kotlinx.io.streams.asInput
import kotlinx.io.streams.asOutput
import java.io.FileNotFoundException
import java.util.*

class OmegaUriFile(val uri: Uri, name: String?, mimeType: String?) : OmegaFile {

    override val type: Type = Type.FILE

    override val mimeType: String = mimeType ?: MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            ?.apply { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.US)) } ?: "*/*"

    override val name: String = name ?: URLUtil.guessFileName(uri.toString(), null, mimeType)

    constructor(uri: String, name: String?, mimeType: String?) : this(Uri.parse(uri), name, mimeType)

    class UriFileInputProcessor(private val contentResolver: ContentResolver) : OmegaFileInputProcessor<OmegaUriFile> {

        constructor(context: Context): this(context.contentResolver)

        override fun OmegaUriFile.extract(): Input? {
            return try {
                contentResolver.openInputStream(uri).asInput()
            } catch (exc: FileNotFoundException) {
                exc.printStackTrace()
                null
            }
        }

    }

    class UriFileOutputProcessor(private val contentResolver: ContentResolver) : OmegaFileOutputProcessor<OmegaUriFile> {

        constructor(context: Context): this(context.contentResolver)

        override fun OmegaUriFile.extract(): Output? {
            return try {
                contentResolver.openOutputStream(uri).asOutput()
            } catch (exc: FileNotFoundException) {
                exc.printStackTrace()
                null
            }
        }

    }

}

@Suppress("unused")
fun OmegaFile.Companion.from(uri: Uri, name: String? = null, mimeType: String? = null) : OmegaFile = OmegaUriFile(uri, name, mimeType)