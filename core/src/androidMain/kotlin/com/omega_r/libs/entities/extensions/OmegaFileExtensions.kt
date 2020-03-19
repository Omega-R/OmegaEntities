package com.omega_r.libs.entities.extensions

import android.net.Uri
import com.omega_r.libs.entities.files.OmegaFile
import com.omega_r.libs.entities.files.java_file.from
import com.omega_r.libs.entities.files.uri.from
import com.omega_r.libs.entities.files.url.from
import java.io.File

val Uri.file
    get() = OmegaFile.from(this)

fun File.toOmegaFile() = OmegaFile.from(this)

val String.file
    get() = OmegaFile.from(this)