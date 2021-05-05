package com.github.bawkses.alcalc.data.source.azurapi.io.output

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslatedStringOutput(
    @SerialName("en")
    val value: String
)