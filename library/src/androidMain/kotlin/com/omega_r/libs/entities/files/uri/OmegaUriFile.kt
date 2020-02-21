package com.omega_r.libs.entities.files.uri

import android.net.Uri
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.Type
import java.util.*

class OmegaUriFile(val uri: Uri, name: String?, mimeType: String?) : OmegaFile {

    override val type: Type = Type.FILE

    override val mimeType: String = mimeType ?: MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            ?.apply { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.US)) } ?: "*/*"

    override val name: String = name ?: URLUtil.guessFileName(uri.toString(), null, mimeType)

    constructor(uri: String, name: String?, mimeType: String?) : this(Uri.parse(uri), name, mimeType)

}

@Suppress("unused")
fun OmegaFile.Companion.from(uri: Uri, name: String? = null, mimeType: String? = null): OmegaFile = OmegaUriFile(uri, name, mimeType)