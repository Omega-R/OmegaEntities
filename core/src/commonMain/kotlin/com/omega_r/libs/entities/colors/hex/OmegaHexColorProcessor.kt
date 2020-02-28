package com.omega_r.libs.entities.colors.hex

import com.omega_r.libs.entities.colors.OmegaColorProcessor
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

object OmegaHexColorProcessor : OmegaColorProcessor<OmegaHexColor> {

    override fun getColorInt(entity: OmegaHexColor, extractor: OmegaResourceExtractor): Int {
        // Use a long to avoid rollovers on #ffXXXXXX
        var rawColorString = entity.colorString.removePrefix("#")

        //  RGB -> RRGGBB
        //  ARGB -> AARRGGBB
        rawColorString = when (rawColorString.length) {
            3 -> "${rawColorString[0]}${rawColorString[0]}${rawColorString[1]}${rawColorString[1]}$" +
                    "${rawColorString[2]}${rawColorString[2]}"
            4 -> "${rawColorString[0]}${rawColorString[0]}${rawColorString[1]}${rawColorString[1]}$" +
                    "${rawColorString[2]}${rawColorString[2]}${rawColorString[3]}${rawColorString[3]}"
            else -> rawColorString
        }

        val color: Long = rawColorString.toLong(16)

        return when (rawColorString.length) {
            // Set the alpha value
            6 -> (color or -0x1000000).toInt()
            8 -> color.toInt()

            else -> throw IllegalArgumentException("Unknown color")
        }
    }

}