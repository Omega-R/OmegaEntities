package com.omega_r.libs.entities.texts

data class StringText(val text: String?) : Text {

    override val isEmpty: Boolean = text.isNullOrEmpty()

}