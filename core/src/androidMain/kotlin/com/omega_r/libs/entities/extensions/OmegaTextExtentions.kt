package com.omega_r.libs.entities.extensions

import android.annotation.StringRes
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.text.OmegaText

fun OmegaText.Companion.from(@StringRes id: Int): OmegaText = from(OmegaResource.Text(id))

fun OmegaText.Companion.from(@StringRes id: Int, vararg formatArgs: Any): OmegaText =
    from(OmegaResource.Text(id), *formatArgs)

fun OmegaText.Companion.from(@StringRes id: Int, quantity: Int, vararg formatArgs: Any): OmegaText =
    from(OmegaResource.Plurals(id), quantity, *formatArgs)