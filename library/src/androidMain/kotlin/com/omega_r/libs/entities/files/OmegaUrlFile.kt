package com.omega_r.libs.entities.files

import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import com.omega_r.libs.entities.files.url.OmegaUrlFile
import kotlinx.io.core.Input
import kotlinx.io.core.Output
import kotlinx.io.streams.asInput
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class OmegaUrlFile(val url: String, name: String?, mimeType: String?) : OmegaFile {

    companion object {
        init {
            FileOutputProcessorsHolder.addProcessor(OmegaUrlFile::class, UrlFileOutputProcessor())
        }
    }

    override val type: Type = Type.FILE

    override val mimeType: String = mimeType ?: MimeTypeMap.getFileExtensionFromUrl(url)
            ?.apply { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.US)) } ?: "*/*"

    override val name: String = name ?: URLUtil.guessFileName(url, null, mimeType)

    class UrlFileInputProcessor : OmegaFileInputProcessor<OmegaUrlFile> {

        override fun OmegaUrlFile.extract(): Input? {
            var connection: HttpURLConnection? = null
            try {
                connection = (URL(url).openConnection() as HttpURLConnection).apply {
                    doInput = true
                    connect()
                }
                val readBytes = connection.inputStream.readBytes()
                return readBytes.inputStream().asInput()
            } finally {
                connection?.disconnect()
            }
        }

    }

    class UrlFileOutputProcessor : OmegaFileOutputProcessor<OmegaUrlFile> {

        override fun OmegaUrlFile.extract(): Output? = null

    }

}

@Suppress("unused")
fun OmegaFile.Companion.from(url: String, name: String? = null, mimeType: String? = null) : OmegaFile = com.omega_r.libs.entities.files.url.OmegaUrlFile(url, name, mimeType)