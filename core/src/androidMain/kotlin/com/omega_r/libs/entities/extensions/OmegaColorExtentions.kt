package com.omega_r.libs.entities.extensions

import android.annotation.ColorInt
import com.omega_r.libs.entities.colors.OmegaColor
import com.omega_r.libs.entities.resources.OmegaResource

fun OmegaColor.Companion.fromResource(@ColorInt id: Int) = fromResource(OmegaResource.Color(id))

fun Int.toOmegaColor(): OmegaColor = OmegaColor.fromResource(this)