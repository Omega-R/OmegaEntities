package com.omega_r.libs.entities.images

import java.io.File

data class OmegaJavaFileImage(val file: File) : BaseBitmapImage(), OmegaImage

fun OmegaImage.Companion.from(file: File): OmegaImage = OmegaJavaFileImage(file)