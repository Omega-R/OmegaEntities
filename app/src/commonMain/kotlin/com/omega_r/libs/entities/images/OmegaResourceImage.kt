package com.omega_r.libs.entities.images

import com.omega_r.libs.entities.resources.OmegaResource

expect class OmegaResourceImage : OmegaImage {

    val resource: OmegaResource.Image

}