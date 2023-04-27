import com.omega_r.libs.entities.files.url.OmegaUrlFile
import kotlin.test.Test

class OmegaEntitiesIosTest {

    @Test
    fun testUrl() {

        val file = OmegaUrlFile(
            url = "https://netrinoimages.s3.eu-west-2.amazonaws.com/2015/07/14/391543/253748/sci_fi_city_3d_model_c4d_max_obj_fbx_ma_lwo_3ds_3dm_stl_2663526_o.jpg"
        )
        println("${file.type} ${file.name} ${file.mimeType}")

    }

}