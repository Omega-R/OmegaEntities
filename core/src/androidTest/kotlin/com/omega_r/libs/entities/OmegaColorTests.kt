package com.omega_r.libs.entities

import com.omega_r.libs.entities.colors.OmegaColor
import com.omega_r.libs.entities.colors.argb.OmegaArgbColor
import com.omega_r.libs.entities.colors.hex.OmegaHexColor
import com.omega_r.libs.entities.colors.integer.OmegaIntColor
import com.omega_r.libs.entities.colors.name.OmegaNameColor
import org.junit.Test

private const val DEFAULT_COLOR_INT = 16764133
private const val HEX_COLOR = "00FFCCE5"

class OmegaColorTests {

    @Test
    fun argbColorTest() {
        val color = OmegaColor.fromArgb(0, 255, 204, 229)
        assert(color is OmegaArgbColor)

        val colorInt = color.getColorInt()
        assert(colorInt == DEFAULT_COLOR_INT)
    }

    @Test
    fun hexColorTest() {
        val color = OmegaColor.fromHex(HEX_COLOR)
        assert(color is OmegaHexColor)

        val colorInt = color.getColorInt()
        assert(colorInt == DEFAULT_COLOR_INT)
    }

    @Test
    fun intColorTest() {
        val color = OmegaColor.fromInt(DEFAULT_COLOR_INT)
        assert(color is OmegaIntColor)
        assert(color.getColorInt() == DEFAULT_COLOR_INT)
    }

    @Test
    fun nameColorTest() {
        val color = OmegaColor.fromName("fuchsia")
        assert(color is OmegaNameColor)
        assert(color.getColorInt() == OmegaColor.FUCHSIA.colorInt)
    }

    @Test
    fun unknownNameColorTest() {
        val color = OmegaColor.fromName("fuchsiaaa")
        assert(color is OmegaNameColor)
        assert(color.getColorInt() == OmegaColor.BLACK.colorInt)
    }

}