package com.omega_r.libs.entities.text.processor

import com.omega_r.libs.entities.processors.OmegaProcessor
import com.omega_r.libs.entities.text.OmegaText
import com.omega_r.libs.entities.text.styles.OmegaTextStyle

interface OmegaTextProcessor<T : OmegaText> : OmegaProcessor<T, CharSequence?>
