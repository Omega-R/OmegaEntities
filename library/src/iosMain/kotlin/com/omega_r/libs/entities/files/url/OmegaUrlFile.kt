package com.omega_r.libs.entities.files.url

import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.Type

actual class OmegaUrlFile(actual val url: String) : OmegaFile {

    override val type: Type = Type.FILE

    override val mimeType: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}