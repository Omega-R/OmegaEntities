import com.omega_r.libs.entities.extensions.toRgbInt
import com.omega_r.libs.entities.extensions.toUIColor
import com.omega_r.libs.entities.files.uri.OmegaUriFile
import com.omega_r.libs.entities.files.url.OmegaUrlFile
import kotlinx.cinterop.pointed
import platform.CoreGraphics.CGFloat
import platform.UIKit.UIColor
import kotlin.test.Test

class OmegaEntitiesIosTest {

    @Test
    fun testUrl() {

        val file = OmegaUrlFile(
            url = "https://netrinoimages.s3.eu-west-2.amazonaws.com/2015/07/14/391543/253748/sci_fi_city_3d_model_c4d_max_obj_fbx_ma_lwo_3ds_3dm_stl_2663526_o.jpg"
        )
        println("${file.type} ${file.name} ${file.mimeType}")

    }

    @Test
    fun testColor() {
        val color = UIColor.orangeColor
        println("TO INT: ${color.toRgbInt()} REVERSE: ${color.toRgbInt()?.toUIColor()?.toRgbInt()}")
    }

}