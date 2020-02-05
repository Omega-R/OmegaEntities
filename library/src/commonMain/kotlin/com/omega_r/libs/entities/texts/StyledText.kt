package com.omega_r.libs.entities.texts

import com.omega_r.libs.entities.texts.styles.TextStyle

data class StyledText(val text: Text, val style: TextStyle) : Text {

    override val isEmpty: Boolean = text.isEmpty

}