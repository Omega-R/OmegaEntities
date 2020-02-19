package com.omega_r.entities

import android.app.Application
import com.omega_r.libs.entities.text.OmegaTextProcessorsHolder
import com.omega_r.libs.entities.text.resource.OmegaResourceText
import com.omega_r.libs.entities.text.resource.OmegaResourceTextProcessor
import com.omega_r.libs.entities.text.resource.OmegaResources

class OEApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        OmegaResources.resources = resources
    }

}