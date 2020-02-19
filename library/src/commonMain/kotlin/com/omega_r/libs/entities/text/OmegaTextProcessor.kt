package com.omega_r.libs.entities.text

import com.omega_r.libs.entities.processors.OmegaProcessor

interface OmegaTextProcessor<T : OmegaText> : OmegaProcessor<T, CharSequence?>
