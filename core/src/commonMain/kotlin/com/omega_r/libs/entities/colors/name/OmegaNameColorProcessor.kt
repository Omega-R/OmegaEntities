package com.omega_r.libs.entities.colors.name

import com.omega_r.libs.entities.colors.OmegaColor
import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

object OmegaNameColorProcessor : OmegaColorProcessor<OmegaNameColor> {

    val colorNameMap = mutableMapOf(
        "black" to OmegaColor.BLACK,
        "darkgray" to OmegaColor.DARK_GRAY,
        "gray" to OmegaColor.GRAY,
        "lightgray" to OmegaColor.LIGHT_GRAY,
        "white" to OmegaColor.WHITE,
        "red" to OmegaColor.RED,
        "green" to OmegaColor.GREEN,
        "blue" to OmegaColor.BLUE,
        "yellow" to OmegaColor.YELLOW,
        "cyan" to OmegaColor.CYAN,
        "magenta" to OmegaColor.MAGENTA,
        "aqua" to OmegaColor.AQUA,
        "fuchsia" to OmegaColor.FUCHSIA,
        "darkgrey" to OmegaColor.DARK_GRAY,
        "grey" to OmegaColor.GRAY,
        "lightgrey" to OmegaColor.LIGHT_GRAY,
        "lime" to OmegaColor.LIME,
        "maroon" to OmegaColor.MAROON,
        "navy" to OmegaColor.NAVY,
        "olive" to OmegaColor.OLIVE,
        "purple" to OmegaColor.PURPLE,
        "silver" to OmegaColor.SILVER,
        "teal" to OmegaColor.TEAL,
        "transparent" to OmegaColor.TRANSPARENT
    )

    override fun getColorInt(entity: OmegaNameColor, extractor: OmegaResourceExtractor): Int {
        return (colorNameMap[entity.colorName.toLowerCase()] ?: OmegaColor.BLACK).colorInt
    }

}