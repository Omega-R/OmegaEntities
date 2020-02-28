package com.omega_r.libs.entities.text.resource.text

import com.omega_r.libs.entities.resources.OmegaResource
import com.omega_r.libs.entities.text.resource.OmegaResourceText

class OmegaTextResourceText(
    resource: OmegaResource.Text,
    vararg formatArgs: Any
) : OmegaResourceText<OmegaResource.Text>(resource, *formatArgs)