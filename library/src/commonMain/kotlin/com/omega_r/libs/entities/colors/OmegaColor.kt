package com.omega_r.libs.entities.colors

import com.omega_r.libs.entities.OmegaEntity
import com.omega_r.libs.entities.colors.argb.OmegaArgbColor
import com.omega_r.libs.entities.colors.hex.OmegaHexColor
import com.omega_r.libs.entities.colors.integer.OmegaIntColor
import com.omega_r.libs.entities.colors.name.OmegaNameColor
import com.omega_r.libs.entities.colors.resource.OmegaResourceColor
import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.resources.OmegaResourceExtractor

interface OmegaColor : OmegaEntity {

    companion object {

        val BLACK = OmegaIntColor(-0x1000000)
        val DARK_GRAY = OmegaIntColor(-0xbbbbbc)
        val GRAY = OmegaIntColor(-0x777778)
        val LIGHT_GRAY = OmegaIntColor(-0x333334)
        val WHITE = OmegaIntColor(-0x1)
        val RED = OmegaIntColor(-0x10000)
        val GREEN = OmegaIntColor(-0xff0100)
        val BLUE = OmegaIntColor(-0xffff01)
        val YELLOW = OmegaIntColor(-0x100)
        val CYAN = OmegaIntColor(-0xff0001)
        val MAGENTA = OmegaIntColor(-0xff01)
        val AQUA = OmegaIntColor(-0xff0001)
        val FUCHSIA = OmegaIntColor(-0xff01)
        val LIME = OmegaIntColor(-0xff0100)
        val MAROON = OmegaIntColor(-0x800000)
        val NAVY = OmegaIntColor(-0xffff80)
        val OLIVE = OmegaIntColor(-0x7f8000)
        val PURPLE = OmegaIntColor(-0x7fff80)
        val SILVER = OmegaIntColor(-0x3f3f40)
        val TEAL = OmegaIntColor(-0xff7f80)
        val TRANSPARENT = OmegaIntColor(0)

        fun fromInt(colorInt: Int) = OmegaIntColor(colorInt)

        fun fromResource(resource: OmegaResource.Colour) = OmegaResourceColor(resource)

        fun fromHex(hex: String) = OmegaHexColor(hex)

        fun fromName(name: String) = OmegaNameColor(name)

        fun fromArgb(alpha: Int, red: Int, green: Int, blue: Int) = OmegaArgbColor(alpha, red, green, blue)

    }

    @Suppress("UNCHECKED_CAST")
    fun getColorInt(
        holder: OmegaColorProcessorsHolder = OmegaColorProcessorsHolder.current,
        extractor: OmegaResourceExtractor = OmegaResourceExtractor.Default
    ): Int {
        return holder.getProcessor(this).getColorInt(this)
    }

}