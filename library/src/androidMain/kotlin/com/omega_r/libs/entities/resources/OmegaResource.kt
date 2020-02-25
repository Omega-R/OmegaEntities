package com.omega_r.libs.entities.resources

import android.annotation.*

actual sealed class OmegaResource {

    actual data class Text(@StringRes val id: Int): OmegaResource()

    actual data class Plurals(@PluralsRes val id: Int): OmegaResource()

    actual data class TextArray(@ArrayRes val id: Int) : OmegaResource()

    actual data class Color(@ColorRes val id: Int, val string: String) : OmegaResource()

    actual data class Image(@DrawableRes val id: Int) : OmegaResource()

}