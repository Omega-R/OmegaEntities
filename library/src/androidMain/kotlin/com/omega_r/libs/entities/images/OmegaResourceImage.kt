package com.omega_r.libs.entities.images

import android.annotation.DrawableRes

data class OmegaResourceImage(@DrawableRes private val drawableRes: Int) : OmegaImage

fun OmegaImage.Companion.from(@DrawableRes drawableRes: Int): OmegaImage = OmegaResourceImage(drawableRes)