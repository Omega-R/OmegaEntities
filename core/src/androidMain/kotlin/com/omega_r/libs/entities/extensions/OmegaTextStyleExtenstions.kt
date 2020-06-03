package com.omega_r.libs.entities.extensions

import android.annotation.ColorInt
import com.omega_r.libs.entities.colors.OmegaColor
import com.omega_r.libs.entities.text.styled.styles.OmegaTextStyle

fun OmegaTextStyle.Companion.from(@ColorInt id: Int): OmegaTextStyle =
    from(OmegaColor.fromResource(id))