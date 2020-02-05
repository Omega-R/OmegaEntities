package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.text.styles.TextStyle

data class StyledText(val sourceText: Text, val style: TextStyle) : Text {

    override val isEmpty: Boolean = sourceText.isEmpty

}