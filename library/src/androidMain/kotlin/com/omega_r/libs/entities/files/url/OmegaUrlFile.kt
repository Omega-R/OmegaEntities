package com.omega_r.libs.entities.files.url

import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.Type
import java.util.*

@Suppress("CanBeParameter")
actual class OmegaUrlFile(
        actual val url: String,
        mimeType: String? = null,
        name: String? = null
) : OmegaFile {

    override val mimeType: String = mimeType ?: MimeTypeMap.getFileExtensionFromUrl(url)
            ?.apply { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.US)) } ?: "*/*"

    override val name: String = name ?: URLUtil.guessFileName(url, null, mimeType)

    override val type: Type = Type.FILE

}

@Suppress("unused")
fun OmegaFile.Companion.from(url: String, mimeType: String? = null, name: String? = null): OmegaFile = OmegaUrlFile(url, name, mimeType)