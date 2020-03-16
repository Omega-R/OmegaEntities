package com.omega_r.libs.entities.files.url

import com.omega_r.libs.entities.files.OmegaFile

expect class OmegaUrlFile: OmegaFile {

    val url: String

}