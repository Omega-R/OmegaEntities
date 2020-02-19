package com.omega_r.libs.entities

import android.annotation.ArrayRes
import android.annotation.PluralsRes
import android.annotation.StringRes

actual sealed class OmegaResource {

    actual data class Text(@StringRes val id: Int): OmegaResource()
    actual data class Plurals(@PluralsRes val id: Int): OmegaResource()
    actual data class TextArray(@ArrayRes val id: Int) : OmegaResource()

}