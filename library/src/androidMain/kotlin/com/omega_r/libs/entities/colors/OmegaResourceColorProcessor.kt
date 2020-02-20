package com.omega_r.libs.entities.colors

import android.annotation.SuppressLint
import android.content.Context
import com.omega_r.libs.entities.colors.resource.OmegaResourceColor

class OmegaResourceColorProcessor(private val context: Context) :
    OmegaColorProcessor<OmegaResourceColor<Int>> {

    private val resources = context.resources

    @Suppress("DEPRECATION")
    @SuppressLint("ObsoleteSdkInt")
    override fun OmegaResourceColor<Int>.extract(): Int? =
            if (SDK_INT >= VERSION_CODES.M) context.getColor(resource.id) else resources.getColor(resource.id)

}